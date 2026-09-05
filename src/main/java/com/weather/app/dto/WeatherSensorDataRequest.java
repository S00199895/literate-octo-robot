package com.weather.app.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSensorDataRequest {

    BigDecimal temperature;

    BigDecimal humidity;

    BigDecimal windSpeed;

    LocalDateTime timestamp;
}
