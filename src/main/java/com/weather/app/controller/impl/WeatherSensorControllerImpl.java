package com.weather.app.controller.impl;

import com.weather.app.controller.WeatherSensorController;
import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.dto.WeatherSensorResponse;
import com.weather.app.service.WeatherSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WeatherSensorControllerImpl implements WeatherSensorController {

    private final WeatherSensorService weatherSensorService;

    @Override
    public ResponseEntity<List<WeatherSensorResponse>> getSensorStatistics(List<Long> sensors, String stat, List<String> metrics, LocalDateTime startDate, LocalDateTime endDate) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(weatherSensorService.getSensorStatistics(sensors, stat, metrics, startDate, endDate));
    }

    @Override
    public ResponseEntity<WeatherSensorDataCreatedResponse> createSensorStatistic(Long id, WeatherSensorDataRequest weatherSensorDataRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(weatherSensorService.createSensorData(id, weatherSensorDataRequest));
    }
}
