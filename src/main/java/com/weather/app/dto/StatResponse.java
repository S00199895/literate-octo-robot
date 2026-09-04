package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class StatResponse {

    String statistic;

    BigDecimal value;

    LocalDateTime startDate;

    LocalDateTime endDate;

    //Long sensorId; //todo should this be mapped if its already nested?
}
