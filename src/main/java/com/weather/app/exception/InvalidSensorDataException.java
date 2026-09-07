package com.weather.app.exception;

import com.weather.app.dto.ValidationError;

import java.util.List;

public class InvalidSensorDataException extends RuntimeException {
    public InvalidSensorDataException(String message) {
        super(message);
    }

    public InvalidSensorDataException(List<ValidationError> validationErrorList) {
        super(validationErrorList.toString());
    }

    public InvalidSensorDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
