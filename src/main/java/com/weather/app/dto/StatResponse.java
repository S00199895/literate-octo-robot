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
}
