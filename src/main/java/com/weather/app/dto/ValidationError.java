package com.weather.app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {

    String field;

    String message;

    @Override
    public String toString() {
        return String.format("Field: %s, Message: %s", field, message);
    }
}
