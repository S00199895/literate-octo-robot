package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class WeatherSensorDataCreatedResponse {

    Long sensorId;

    BigDecimal temperature;

    BigDecimal humidity;

    BigDecimal windSpeed;

    LocalDateTime timestamp;
}
