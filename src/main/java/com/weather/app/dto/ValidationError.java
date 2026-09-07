package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValidationError {

    String field;

    String message;

    @Override
    public String toString() {
        return String.format("Field: %s, Message: %s", field, message);
    }
}
