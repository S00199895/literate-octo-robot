package com.weather.app.common;

import com.weather.app.dto.*;
import com.weather.app.model.entity.WeatherSensorEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static java.util.Collections.emptyList;

public class TestConstants {

    public static final Instant FIXED_INSTANT = Instant.parse("2026-09-05T12:00:00.00Z");

    public static final Long SENSOR_ID_1 = 1L;

    public static final Long SENSOR_ID_2 = 2L;

    public static final Long[] SENSOR_IDS = new Long[] {SENSOR_ID_1, SENSOR_ID_2};

    public static final List<Long> SENSOR_IDS_LIST = List.of(SENSOR_IDS);

    public static final String MIN_STAT = "min";

    public static final String MAX_STAT = "max";

    public static final String AVERAGE_STAT = "average";

    public static final String TEMPERATURE_METRIC = "temperature";

    public static final String HUMIDITY_METRIC = "humidity";

    public static final String WIND_SPEED_METRIC = "windSpeed";

    public static final List<String> ALL_METRICS = List.of(TEMPERATURE_METRIC, HUMIDITY_METRIC, WIND_SPEED_METRIC);

    public static final LocalDateTime TIMESTAMP_1 = LocalDateTime.parse("2026-12-03T10:15:30");

    public static final LocalDateTime START_DATE_1 = LocalDateTime.parse("2026-12-05T10:15:30");

    public static final LocalDateTime END_DATE_1 = LocalDateTime.parse("2026-12-06T10:15:30");

    public static final BigDecimal TEMPERATURE_1 = new BigDecimal("10");

    public static final BigDecimal HUMIDITY_1 = new BigDecimal("17");

    public static final BigDecimal WIND_SPEED_1 = new BigDecimal("20");

    public static final BigDecimal TEMPERATURE_2 = new BigDecimal("1");

    public static final BigDecimal HUMIDITY_2 = new BigDecimal("1");

    public static final BigDecimal WIND_SPEED_2 = new BigDecimal("1");

    public static final BigDecimal TEMPERATURE_3 = new BigDecimal("5");

    public static final BigDecimal HUMIDITY_3 = new BigDecimal("5");

    public static final BigDecimal WIND_SPEED_3 = new BigDecimal("5");

    public static final BigDecimal INVALID_HUMIDITY_1 = new BigDecimal("-12");

    public static final BigDecimal INVALID_WIND_SPEED_1 = new BigDecimal("-15");

    public static final Long WEATHER_ENTITY_ID_1 = 1L;

    public static final String INVALID_STAT = "MINZ";

    public static final List<String> INVALID_METRICS = List.of("AVG", "MA", "SUUM");

    public static final ValidationError VALIDATION_ERROR_1 = ValidationError.builder()
            .field("id")
            .message("sensor id cannot be null")
            .build();

    public static final ValidationError VALIDATION_ERROR_2 = ValidationError.builder()
            .field("windSpeed")
            .message("Invalid value for windSpeed: " + INVALID_WIND_SPEED_1)
            .build();

    public static final ValidationError VALIDATION_ERROR_3 = ValidationError.builder()
            .field("humidity")
            .message("Invalid value for humidity: " + INVALID_HUMIDITY_1)
            .build();

    public static final List<ValidationError> VALIDATION_ERROR_LIST = List.of(VALIDATION_ERROR_1, VALIDATION_ERROR_2, VALIDATION_ERROR_3);

    public static final String VALIDATION_ERRORS_STRING = String.format("[Field: id, Message: sensor id cannot be null, Field: windSpeed, Message: Invalid value for windSpeed: %s, Field: humidity, Message: Invalid value for humidity: %s]", INVALID_WIND_SPEED_1, INVALID_HUMIDITY_1);

    public static final ValidationError VALIDATION_ERROR_QUERY_1 = ValidationError.builder()
            .field("stat")
            .message("Invalid stat: " + INVALID_STAT)
            .build();

    public static final ValidationError VALIDATION_ERROR_QUERY_2 = ValidationError.builder()
            .field("metrics")
            .message("Invalid metrics: " + INVALID_METRICS)
            .build();

    public static final WeatherSensorDataRequest getWeatherSensorDataRequest() {

        return WeatherSensorDataRequest
                .builder()
                .temperature(TEMPERATURE_1)
                .humidity(HUMIDITY_1)
                .windSpeed(WIND_SPEED_1)
                .timestamp(TIMESTAMP_1)
                .build();
    }

    public static final WeatherSensorDataRequest getInvalidWeatherSensorDataRequest() {

        return WeatherSensorDataRequest
                .builder()
                .temperature(TEMPERATURE_1)
                .humidity(INVALID_HUMIDITY_1)
                .windSpeed(INVALID_WIND_SPEED_1)
                .timestamp(TIMESTAMP_1)
                .build();
    }

    public static final WeatherSensorDataCreatedResponse getWeatherSensorDataCreatedResponse() {

        return WeatherSensorDataCreatedResponse
                .builder()
                .sensorId(SENSOR_ID_1)
                .temperature(TEMPERATURE_1)
                .humidity(HUMIDITY_1)
                .windSpeed(WIND_SPEED_1)
                .timestamp(TIMESTAMP_1)
                .build();
    }

    public static final WeatherSensorEntity getWeatherSensorEntity() {

        return WeatherSensorEntity
                .builder()
                .sensorId(SENSOR_ID_1)
                .temperature(TEMPERATURE_1)
                .humidity(HUMIDITY_1)
                .windSpeed(WIND_SPEED_1)
                .readingTimestamp(TIMESTAMP_1)
                .build();
    }

    public static final WeatherSensorEntity getSavedWeatherSensorEntity() {

        return WeatherSensorEntity
                .builder()
                .id(WEATHER_ENTITY_ID_1)
                .sensorId(SENSOR_ID_1)
                .temperature(TEMPERATURE_1)
                .humidity(HUMIDITY_1)
                .windSpeed(WIND_SPEED_1)
                .readingTimestamp(TIMESTAMP_1)
                .build();
    }

    public static final ValidatedQueryResult getValidatedQueryResult() {

        return ValidatedQueryResult.builder()
                .validatedStartDate(START_DATE_1)
                .validatedEndDate(END_DATE_1)
                .validationErrors(emptyList())
                .build();
    }

    public static final List<ValidationError> VALIDATION_ERRORS_QUERY = List.of(VALIDATION_ERROR_QUERY_1, VALIDATION_ERROR_QUERY_2);

    public static final LocalDateTime FIXED_DEFAULT_START_LOCAL_DATE_TIME = LocalDateTime.ofInstant(FIXED_INSTANT, ZoneId.systemDefault()).minusDays(2);

    public static final LocalDateTime FIXED_DEFAULT_END_LOCAL_DATE_TIME = LocalDateTime.ofInstant(FIXED_INSTANT, ZoneId.systemDefault()).minusDays(1);

    public static final ValidatedQueryResult getValidatedQueryResultWithErrors() {

        return ValidatedQueryResult.builder()
                .validatedStartDate(FIXED_DEFAULT_START_LOCAL_DATE_TIME)
                .validatedEndDate(FIXED_DEFAULT_END_LOCAL_DATE_TIME)
                .validationErrors(VALIDATION_ERRORS_QUERY)
                .build();
    }

    public static final SensorMetric SENSOR_METRIC_1 = SensorMetric.builder()
            .sensorId(SENSOR_ID_1)
            .humidity(HUMIDITY_2)
            .temperature(TEMPERATURE_2)
            .windSpeed(WIND_SPEED_2)
            .build();

    public static final SensorMetric SENSOR_METRIC_2 = SensorMetric.builder()
            .sensorId(SENSOR_ID_2)
            .humidity(HUMIDITY_3)
            .temperature(TEMPERATURE_3)
            .windSpeed(WIND_SPEED_3)
            .build();

    public static final List<SensorMetric> SENSOR_METRICS = List.of(SENSOR_METRIC_1, SENSOR_METRIC_2);

    public static final List<BigDecimal> TEMPERATURE_2_LIST = List.of(TEMPERATURE_2);
    public static final List<BigDecimal> HUMIDITY_2_LIST = List.of(HUMIDITY_2);
    public static final List<BigDecimal> WIND_SPEED_2_LIST = List.of(WIND_SPEED_2);

    public static final List<BigDecimal> TEMPERATURE_3_LIST = List.of(TEMPERATURE_3);
    public static final List<BigDecimal> HUMIDITY_3_LIST = List.of(HUMIDITY_3);
    public static final List<BigDecimal> WIND_SPEED_3_LIST = List.of(WIND_SPEED_3);

    public static final StatResponse STAT_RESPONSE_TEMPERATURE_2 = StatResponse.builder()
            .statistic(MIN_STAT)
            .metric(TEMPERATURE_METRIC)
            .value(TEMPERATURE_2)
            .build();

    public static final StatResponse STAT_RESPONSE_HUMIDITY_2 = StatResponse.builder()
            .statistic(MIN_STAT)
            .metric(HUMIDITY_METRIC)
            .value(HUMIDITY_2)
            .build();

    public static final StatResponse STAT_RESPONSE_WIND_SPEED_2 = StatResponse.builder()
            .statistic(MIN_STAT)
            .metric(WIND_SPEED_METRIC)
            .value(WIND_SPEED_3)
            .build();

    public static final List<StatResponse> STAT_RESPONSE_LIST_2 = List.of(STAT_RESPONSE_TEMPERATURE_2, STAT_RESPONSE_HUMIDITY_2, STAT_RESPONSE_WIND_SPEED_2);

    public static final StatResponse STAT_RESPONSE_TEMPERATURE_3 = StatResponse.builder()
            .statistic(MIN_STAT)
            .metric(TEMPERATURE_METRIC)
            .value(TEMPERATURE_3)
            .build();

    public static final StatResponse STAT_RESPONSE_HUMIDITY_3 = StatResponse.builder()
            .statistic(MIN_STAT)
            .metric(HUMIDITY_METRIC)
            .value(HUMIDITY_3)
            .build();

    public static final StatResponse STAT_RESPONSE_WIND_SPEED_3 = StatResponse.builder()
            .statistic(MIN_STAT)
            .metric(WIND_SPEED_METRIC)
            .value(WIND_SPEED_2)
            .build();

    public static final List<StatResponse> STAT_RESPONSE_LIST_3 = List.of(STAT_RESPONSE_TEMPERATURE_3, STAT_RESPONSE_HUMIDITY_3, STAT_RESPONSE_WIND_SPEED_3);

    public static final WeatherSensorStatisticResponse WEATHER_SENSOR_RESPONSE_1 = WeatherSensorStatisticResponse.builder()
            .id(SENSOR_ID_1)
            .startDate(START_DATE_1)
            .endDate(END_DATE_1)
            .statistics(STAT_RESPONSE_LIST_2)
            .build();

    public static final WeatherSensorStatisticResponse WEATHER_SENSOR_RESPONSE_2 = WeatherSensorStatisticResponse.builder()
            .id(SENSOR_ID_2)
            .startDate(START_DATE_1)
            .endDate(END_DATE_1)
            .statistics(STAT_RESPONSE_LIST_3)
            .build();

    public static final List<WeatherSensorStatisticResponse> WEATHER_SENSOR_RESPONSES = List.of(WEATHER_SENSOR_RESPONSE_1, WEATHER_SENSOR_RESPONSE_2);
}
