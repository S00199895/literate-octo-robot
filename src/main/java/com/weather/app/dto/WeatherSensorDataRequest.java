package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class WeatherSensorDataRequest {

    Long sensorId; //todo should this be on the url instead

    BigDecimal temperature;

    BigDecimal humidity;

    BigDecimal windSpeed;

    LocalDateTime timestamp;
}
