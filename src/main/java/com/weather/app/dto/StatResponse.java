package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class StatResponse {

    String statistic;

    String metric;

    BigDecimal value;

    /*
    *
    * BigDecimal temperature
    *
    * BigDecimal humidity
    *
    * BigDecimal windSpeed
    * */

    //Long sensorId; //todo should this be mapped if its already nested? - i dont think so
}
