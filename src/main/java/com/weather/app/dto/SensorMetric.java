package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class SensorMetric {

    Long sensorId;

//    BigDecimal value;


    BigDecimal temperature;

    BigDecimal humidity;

    BigDecimal windSpeed;

}
