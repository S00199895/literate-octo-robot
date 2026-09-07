package com.weather.app.service;

import com.weather.app.dto.StatResponse;

import java.math.BigDecimal;
import java.util.List;

public interface StatService {

    /**
     *
     * @param metricValues the values to be used in calculation
     * @return the smallest value from the list
     */
    BigDecimal calculateMin(List<BigDecimal> metricValues);

    /**
     *
     * @param metricValues the values to be used in calculation
     * @return the largest value from the list
     */
    BigDecimal calculateMax(List<BigDecimal> metricValues);

    /**
     *
     * @param metricValues the values to be summed
     * @return the sum of the values
     */
    BigDecimal calculateSum(List<BigDecimal> metricValues);

    /**
     *
     * @param metricValues the values to be used in calculation
     * @return the mean of the values
     */
    BigDecimal calculateAverage(List<BigDecimal> metricValues);

    /**
     *
     * @param metricValues the values to be passed for calculation
     * @param stat         the operation to be performed - min, max, average, sum
     * @param metric       the requested metric - temperature, humidity, windSpeed
     * @return a StatResponse with the result, metric, and stat requested
     */
    StatResponse calculateStat(List<BigDecimal> metricValues, String stat, String metric);
}
