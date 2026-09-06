package com.weather.app.service.impl;

import com.weather.app.dto.*;
import com.weather.app.exception.InvalidSensorDataException;
import com.weather.app.mapper.WeatherSensorMapper;
import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import com.weather.app.service.StatService;
import com.weather.app.service.ValidationService;
import com.weather.app.service.WeatherSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.weather.app.common.Constants.*;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WeatherSensorServiceImpl implements WeatherSensorService {

    private final WeatherSensorRepository weatherSensorRepository;

    private final WeatherSensorMapper weatherSensorMapper;

    private final StatService statService;

    private final ValidationService validationService;


    @Override
    public List<WeatherSensorResponse> getSensorStatistics(List<Long> sensors, String stat, List<String> metrics, LocalDateTime startDate, LocalDateTime endDate) {
        
        final ValidatedQueryResult validatedQueryResult = validationService.validateSensorQueryParams(stat, metrics, startDate, endDate);

        if (!validatedQueryResult.getValidationErrors().isEmpty()) {
            throw new InvalidSensorDataException(validatedQueryResult.getValidationErrors());
        }

        final LocalDateTime validatedStartDate = validatedQueryResult.getValidatedStartDate();
        final LocalDateTime validatedEndDate = validatedQueryResult.getValidatedEndDate();

        final List<WeatherSensorResponse> weatherSensorResponseList = new ArrayList<>();
        final List<SensorMetric> sensorMetrics;

        //if theres no sensors given, query all sensors data SEPARATELY
        //so we'll need one query that does all (group by something?)


        if (isNull(sensors) || sensors.isEmpty()) { //todo only check tge sensors if there is any
            sensorMetrics = weatherSensorRepository.findAllSensorsMetricsBetweenDates(validatedStartDate, validatedEndDate);

        } else {
            sensorMetrics = weatherSensorRepository.findSensorMetricsBySensorIdsBetweenDates(sensors.toArray(Long[]::new), validatedStartDate, validatedEndDate);
        }

        final List<Long> returnedSensorIds = sensorMetrics.stream().map(SensorMetric::getSensorId).distinct().toList();

        /**
         * at this stage then, we want to build out our actual response WeatherSensorResponse
         * has the sensor id, and then the list of stats
         *
         * do we want to filter straight away by sensor id, and then make a separate one for each of those
         * map of sensor id to list of metrics?
         * because we want to get the average, etc operation of the sensorMetric.value of all of the ones
         * filter by id first
         * then you'll get the list of sensor metric per sensor
         * then start constructing a list out of the operations you wanted (stats)
         *
         * depending on the stats given, run separate streams for those in private (?) methods
         * then add that to the list of stats for the sensor
         * */

        returnedSensorIds.forEach(sensor -> {

//            List<SensorMetric> groupedSensorMetrics;

            //include the metric in this

            final List<StatResponse> statistics = new ArrayList<>();

//            stats.forEach(stat -> {
//
//                switch (stat) {
//                    case MIN_STAT:
//                        statistics.add(StatResponse.builder().statistic(MIN_STAT).value(statService.calculateMin(groupedSensorMetrics)).build());
//                    case MAX_STAT:
//                        statistics.add(StatResponse.builder().statistic(MAX_STAT).value(statService.calculateMax(groupedSensorMetrics)).build());
//                    case AVERAGE_STAT:
//                        statistics.add(StatResponse.builder().statistic(AVERAGE_STAT).value(statService.calculateAverage(groupedSensorMetrics)).build());
//                    case SUM_STAT:
//                        statistics.add(StatResponse.builder().statistic(SUM_STAT).value(statService.calculateSum(groupedSensorMetrics)).build());
//                }
//          //todo we only pass one });

            metrics.forEach(metric -> {

                /*
                * now here we want to switch on the metric in the foreach
                * pass down the operation from the stat
                * so how are we going to pick which method to use
                *
                * if statement
                * */
                StatResponse statResponse = null;
                if (TEMPERATURE_METRIC.equals(metric)) {
                    final List<BigDecimal> temperatureValues = sensorMetrics.stream() //todo should we be calling sensormetrics again down here?
                            .filter(sensorMetric -> sensorMetric.getSensorId().equals(sensor))
                            .map(SensorMetric::getTemperature)
                            .toList();

                    statResponse = statService.calculateStat(temperatureValues, stat, metric);
                } else if (HUMIDITY_METRIC.equals(metric)) {
                    final List<BigDecimal> humidityValues = sensorMetrics.stream()
                            .filter(sensorMetric -> sensorMetric.getSensorId().equals(sensor))
                            .map(SensorMetric::getHumidity)
                            .toList();

                    statResponse = statService.calculateStat(humidityValues, stat, metric);
                } else if (WIND_SPEED_METRIC.equals(metric)) {
                    final List<BigDecimal> windSpeedValues = sensorMetrics.stream()
                            .filter(sensorMetric -> sensorMetric.getSensorId().equals(sensor))
                            .map(SensorMetric::getWindSpeed)
                            .toList();

                    statResponse = statService.calculateStat(windSpeedValues, stat, metric);
                }

                statistics.add(statResponse);
            });



            weatherSensorResponseList.add(WeatherSensorResponse
                    .builder()
                    .id(sensor)
                    .statistics(statistics)
                    .startDate(validatedStartDate)
                    .endDate(validatedEndDate)
                    .build());
        });

        return weatherSensorResponseList;
    }

    @Override
    public WeatherSensorDataCreatedResponse createSensorData(Long id, WeatherSensorDataRequest weatherSensorDataRequest) {

        //todo do you then want to be worrying about different sensors existing?

        final List<ValidationError> validationErrors = validationService.validateSensorDataRequest(id, weatherSensorDataRequest);

        if (!validationErrors.isEmpty()) {
            throw new InvalidSensorDataException(validationErrors);
        }

        final WeatherSensorEntity weatherSensorEntity = weatherSensorMapper.mapSensorDataRequestToEntity(id, weatherSensorDataRequest);

        final WeatherSensorEntity createdWeatherSensorEntity = weatherSensorRepository.save(weatherSensorEntity);

        return weatherSensorMapper.mapSensorDataEntityToCreatedResponse(createdWeatherSensorEntity);
    }
}
