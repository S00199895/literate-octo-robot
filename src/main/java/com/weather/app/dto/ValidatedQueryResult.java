package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class ValidatedQueryResult {

    LocalDateTime validatedStartDate;

    LocalDateTime validatedEndDate;

    List<ValidationError> validationErrors;
}
