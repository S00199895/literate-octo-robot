package com.weather.app.controller.impl;

import com.weather.app.controller.WeatherSensorController;
import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.dto.WeatherSensorResponse;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class WeatherSensorControllerImpl implements WeatherSensorController {

    @Override
    public List<WeatherSensorResponse> getSensorStatistics(List<Long> sensors, List<String> stats, LocalDateTime startDate, LocalDateTime endDate) {
        return List.of();
    }

    @Override
    public WeatherSensorDataCreatedResponse createSensorStatistic(WeatherSensorDataRequest weatherSensorDataRequest) {
        return null;
    }
}
