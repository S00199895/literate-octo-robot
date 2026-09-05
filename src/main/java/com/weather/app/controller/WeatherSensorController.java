package com.weather.app.controller;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.dto.WeatherSensorResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<WeatherSensorResponse>> getSensorStatistics(
            @RequestParam("sensors") List<Long> sensors, //todo should these be arrays or lists?
            @RequestParam("stats") List<String> stats,
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate
            );

    /**
     *
     * @param id the sensor id for the associated data
     * @param weatherSensorDataRequest the data points associated with the sensor
     * @return the saved data points and the associated sensor ID
     */
    @PostMapping("/{id}")
    public ResponseEntity<WeatherSensorDataCreatedResponse> createSensorStatistic(@PathVariable("id") Long id, @RequestBody WeatherSensorDataRequest weatherSensorDataRequest);
}
