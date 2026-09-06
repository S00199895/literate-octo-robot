package com.weather.app.service;

import com.weather.app.dto.StatResponse;

import java.math.BigDecimal;
import java.util.List;

public interface StatService {

    BigDecimal calculateMin(List<BigDecimal> metricValues);

    BigDecimal calculateMax(List<BigDecimal> metricValues);

    BigDecimal calculateSum(List<BigDecimal> metricValues);

    BigDecimal calculateAverage(List<BigDecimal> metricValues);

    StatResponse calculateStat(List<BigDecimal> metricValues, String stat, String metric);
}
