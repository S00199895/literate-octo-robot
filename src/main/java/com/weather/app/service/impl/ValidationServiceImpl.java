package com.weather.app.service.impl;

import com.weather.app.dto.ValidatedQueryResult;
import com.weather.app.dto.ValidationError;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.service.ValidationService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.weather.app.common.Constants.METRICS_ALLOWED;
import static com.weather.app.common.Constants.STATS_ALLOWED;
import static java.util.Objects.isNull;

@Component
public class ValidationServiceImpl implements ValidationService {

    private final Clock clock = Clock.systemDefaultZone();

    @Override
    public ValidatedQueryResult validateSensorQueryParams(final String stat, final List<String> metrics, final LocalDateTime startDate, final LocalDateTime endDate) {

        final List<ValidationError> validationErrors = new ArrayList<>();

        LocalDateTime validatedStartDate = startDate;
        LocalDateTime validatedEndDate = endDate;

        if (Arrays.stream(STATS_ALLOWED).noneMatch(s -> s.equals(stat))) {
            validationErrors.add(ValidationError.builder()
                    .field("stat")
                    .message(String.format("Invalid stat: %s", stat))
                    .build());
        }

        if (!Arrays.asList(METRICS_ALLOWED).containsAll(metrics)) {
            validationErrors.add(ValidationError.builder()
                    .field("metrics")
                    .message(String.format("Invalid metrics: %s", metrics))
                    .build());
        }

        if (isNull(startDate) && isNull(endDate)) {
            validatedStartDate = LocalDateTime.now(clock).minusDays(2L);
            validatedEndDate = LocalDateTime.now(clock).minusDays(1L);
        } else {
            if ((isNull(startDate) || isNull(endDate))
                    || endDate.isBefore(startDate)) {
                validationErrors.add(ValidationError.builder()
                        .field("startDate, endDate")
                        .message(String.format("Invalid date(s) - startDate: %s, endDate: %s", startDate, endDate))
                        .build());
            } else {
                if (startDate.isBefore(LocalDateTime.now(clock).minusMonths(1L))
                        || startDate.isAfter(LocalDateTime.now(clock).minusDays(1L))) {
                    validationErrors.add(ValidationError.builder()
                            .field("startDate")
                            .message("startDate must be valid between 1 month and 1 day ago")
                            .build());
                }

                if (endDate.isAfter(LocalDateTime.now(clock).minusDays(1L))) {
                    validationErrors.add(ValidationError.builder()
                            .field("endDate")
                            .message("endDate must be valid between 1 month and 1 day ago")
                            .build());
                }
            }
        }

        return ValidatedQueryResult.builder().validatedStartDate(validatedStartDate).validatedEndDate(validatedEndDate).validationErrors(validationErrors).build();
    }

    @Override
    public List<ValidationError> validateSensorDataRequest(final Long id, final WeatherSensorDataRequest weatherSensorDataRequest) {

        List<ValidationError> validationErrors = new ArrayList<>();

        if (isNull(weatherSensorDataRequest)) {
            validationErrors.add(ValidationError.builder()
                    .field("weatherSensorDataRequest")
                    .message("request is null")
                    .build());

            return validationErrors;
        }

        if (isNull(weatherSensorDataRequest.getTemperature())) {
            validationErrors.add(ValidationError.builder()
                    .field("temperature")
                    .message("temperature cannot be null")
                    .build());
        }

        if (isNull(weatherSensorDataRequest.getWindSpeed())
                || weatherSensorDataRequest.getWindSpeed().compareTo(BigDecimal.ZERO) < 0) {
            validationErrors.add(ValidationError.builder()
                    .field("windSpeed")
                    .message(String.format("Invalid value for windSpeed: %s", weatherSensorDataRequest.getWindSpeed()))
                    .build());
        }

        if (isNull(weatherSensorDataRequest.getHumidity())
                || weatherSensorDataRequest.getHumidity().compareTo(BigDecimal.ZERO) < 0) {
            validationErrors.add(ValidationError.builder()
                    .field("humidity")
                    .message(String.format("Invalid value for humidity: %s", weatherSensorDataRequest.getHumidity()))
                    .build());
        }

        return validationErrors;
    }
}
