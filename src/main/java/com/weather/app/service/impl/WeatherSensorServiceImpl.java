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
    public List<WeatherSensorStatisticResponse> getSensorStatistics(final List<Long> sensors, final String stat, final List<String> metrics, final LocalDateTime startDate, final LocalDateTime endDate) {
        
        final ValidatedQueryResult validatedQueryResult = validationService.validateSensorQueryParams(stat, metrics, startDate, endDate);

        if (!validatedQueryResult.getValidationErrors().isEmpty()) {
            throw new InvalidSensorDataException(validatedQueryResult.getValidationErrors());
        }

        final LocalDateTime validatedStartDate = validatedQueryResult.getValidatedStartDate();
        final LocalDateTime validatedEndDate = validatedQueryResult.getValidatedEndDate();

        final List<WeatherSensorStatisticResponse> weatherSensorStatisticResponseList = new ArrayList<>();
        final List<SensorMetric> sensorMetrics;

        if (isNull(sensors) || sensors.isEmpty()) {
            sensorMetrics = weatherSensorRepository.findAllSensorsMetricsBetweenDates(validatedStartDate, validatedEndDate);
        } else {
            sensorMetrics = weatherSensorRepository.findSensorMetricsBySensorIdsBetweenDates(sensors.toArray(Long[]::new), validatedStartDate, validatedEndDate);
        }

        final List<Long> returnedSensorIds = sensorMetrics.stream().map(SensorMetric::getSensorId).distinct().toList();

        returnedSensorIds.forEach(sensor -> {

            final List<StatResponse> statistics = new ArrayList<>();

            metrics.forEach(metric -> {

                StatResponse statResponse = null;
                if (TEMPERATURE_METRIC.equals(metric)) {
                    final List<BigDecimal> temperatureValues = sensorMetrics.stream()
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

            weatherSensorStatisticResponseList.add(WeatherSensorStatisticResponse
                    .builder()
                    .id(sensor)
                    .statistics(statistics)
                    .startDate(validatedStartDate)
                    .endDate(validatedEndDate)
                    .build());
        });

        return weatherSensorStatisticResponseList;
    }

    @Override
    public WeatherSensorDataCreatedResponse createSensorData(final Long id, final WeatherSensorDataRequest weatherSensorDataRequest) {

        final List<ValidationError> validationErrors = validationService.validateSensorDataRequest(id, weatherSensorDataRequest);

        if (!validationErrors.isEmpty()) {
            throw new InvalidSensorDataException(validationErrors);
        }

        final WeatherSensorEntity weatherSensorEntity = weatherSensorMapper.mapSensorDataRequestToEntity(id, weatherSensorDataRequest);

        final WeatherSensorEntity createdWeatherSensorEntity = weatherSensorRepository.save(weatherSensorEntity);

        return weatherSensorMapper.mapSensorDataEntityToCreatedResponse(createdWeatherSensorEntity);
    }
}
