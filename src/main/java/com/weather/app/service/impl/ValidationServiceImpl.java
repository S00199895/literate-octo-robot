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
    public ValidatedQueryResult validateSensorQueryParams(String stat, List<String> metrics, LocalDateTime startDate, LocalDateTime endDate) {

        /*
         * sensors - i tihnk we dont need to check this because its just a list of long anyway
         * mayve check its not negative is that too much validation?
         *
         * stat is in the static array - should be an exact match
         * metrics are in the static array
         *
         * startdate has to be greater than a month ago and less than a day ago
         * if no end date - set end date to now
         * if no start date give an error
         * if neither, set start date to a day ago and end date to now
         * */

        List<ValidationError> validationErrors = new ArrayList<>();

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
            if (isNull(startDate)
                    || startDate.isBefore(LocalDateTime.now(clock).minusMonths(1L))
                    || startDate.isAfter(LocalDateTime.now(clock).minusDays(1L))) {
                validationErrors.add(ValidationError.builder()
                        .field("startDate")
                        .message("startDate must be valid between 1 month and 1 day ago")
                        .build());
            }

            if (isNull(endDate)
                    || endDate.isBefore(startDate)
                    || endDate.isAfter(LocalDateTime.now(clock).minusDays(1L))) {
                validationErrors.add(ValidationError.builder()
                        .field("endDate")
                        .message("endDate must be valid between 1 month and 1 day ago")
                        .build());
            }
        }

        return ValidatedQueryResult.builder().validatedStartDate(validatedStartDate).validatedEndDate(validatedEndDate).validationErrors(validationErrors).build();
    }

    @Override
    public List<ValidationError> validateSensorDataRequest(Long id, WeatherSensorDataRequest weatherSensorDataRequest) {

        List<ValidationError> validationErrors = new ArrayList<>();

        if (isNull(weatherSensorDataRequest)) {
            validationErrors.add(ValidationError.builder()
                    .field("weatherSensorDataRequest")
                    .message("request is null")
                    .build());

            return validationErrors;
        }

        if (isNull(id)) { //todo dont need this if we have the field being mandatory
            validationErrors.add(ValidationError.builder()
                    .field("id")
                    .message("sensor id cannot be null")
                    .build());
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
