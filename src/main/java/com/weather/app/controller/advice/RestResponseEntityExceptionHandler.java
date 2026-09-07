package com.weather.app.controller.advice;

import com.weather.app.exception.InvalidSensorDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidSensorDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidSensorDataException(InvalidSensorDataException e) {

        return ResponseEntity.badRequest().body(Map.of(
                "errorMessage", e.getMessage()
        ));
    }
}
