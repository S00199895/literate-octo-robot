package com.weather.app.controller.advice;

import com.weather.app.exception.InvalidSensorDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    //todo do the error handling next and decide how to display, output that - how much does it matter because it might just be the sensor you're throwing to
    //we dont know that though and thats a consideratopm
    @ExceptionHandler(InvalidSensorDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidSensorDataException(InvalidSensorDataException e) {

        return ResponseEntity.badRequest().body(Map.of(
                "errorMessage", e.getMessage()
        ));
    }
}
