package com.weather.app.service.impl;

import com.weather.app.dto.StatResponse;
import com.weather.app.service.StatService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

import static com.weather.app.common.Constants.*;

@Component
public class StatServiceImpl implements StatService {

    @Override
    public StatResponse calculateStat(final List<BigDecimal> metricValues, final String stat, final String metric) {

        BigDecimal value;

        switch (stat) {
            case MIN_STAT-> value = calculateMin(metricValues);
            case MAX_STAT-> value = calculateMax(metricValues);
            case AVERAGE_STAT-> value = calculateAverage(metricValues);
            case SUM_STAT-> value = calculateSum(metricValues);
            default-> value = calculateAverage(metricValues);
        };

        return StatResponse.builder().statistic(stat).metric(metric).value(value).build();
    }

    @Override
    public BigDecimal calculateMin(final List<BigDecimal> metricValues) {
        return metricValues.stream()
                .min(Comparator.naturalOrder())
                .get();
    }

    @Override
    public BigDecimal calculateMax(final List<BigDecimal> metricValues) {
        return metricValues.stream()
                .max(Comparator.naturalOrder())
                .get();
    }

    @Override
    public BigDecimal calculateSum(final List<BigDecimal> metricValues) {
        return metricValues.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateAverage(final List<BigDecimal> metricValues) {
        return calculateSum(metricValues)
                .divide(new BigDecimal(String.valueOf(metricValues.size())),
                        RoundingMode.HALF_UP);
    }
}
