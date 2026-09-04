package com.weather.app.controller;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.dto.WeatherSensorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(path = "/weather")
public interface WeatherSensorController {

    /**
     *
     * @param sensors
     * @param stats
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping
    public List<WeatherSensorResponse> getSensorStatistics(
            @RequestParam("sensors") List<Long> sensors, //todo should these be arrays or lists?
            @RequestParam("stats") List<String> stats,
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate
            );

    /**
     *
     * @param weatherSensorDataRequest
     * @return
     */
    @PostMapping
    public WeatherSensorDataCreatedResponse createSensorStatistic(@RequestBody WeatherSensorDataRequest weatherSensorDataRequest);
}
