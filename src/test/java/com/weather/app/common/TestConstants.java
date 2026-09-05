package com.weather.app.common;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.model.entity.WeatherSensorEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestConstants {

    public static final Long SENSOR_ID_1 = 1L;

    public static final LocalDateTime TIMESTAMP_1 = LocalDateTime.parse("2026-12-03T10:15:30");

    public static final BigDecimal TEMPERATURE_1 = new BigDecimal("10");

    public static final BigDecimal HUMIDITY_1 = new BigDecimal("17");

    public static final BigDecimal WIND_SPEED_1 = new BigDecimal("20");

    public static final BigDecimal INVALID_HUMIDITY_1 = new BigDecimal("-12");

    public static final BigDecimal INVALID_WIND_SPEED_1 = new BigDecimal("-15");

    public static final Long WEATHER_ENTITY_ID_1 = 1L;


    public static final String VALIDATION_ERRORS_STRING = String.format("[Field: id, Message: sensor id cannot be null, Field: windSpeed, Message: Invalid value for windSpeed: %s, Field: humidity, Message: Invalid value for humidity: %s]", INVALID_WIND_SPEED_1, INVALID_HUMIDITY_1);

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
                .timestamp(TIMESTAMP_1)
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
                .timestamp(TIMESTAMP_1)
                .build();
    }
}
