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
     * @param sensors The IDs of the sensors' data to return. If blank, return all sensors
     * @param stat The statistic to return - required
     * @param metrics the metrics to return - required
     * @param startDate the start date to return data from. Optional - if blank, return today's data
     * @param endDate the end date to return data to. Throws if start date is not supplied with this.
     * @return The sensors and their statistics for the time period
     */
    @GetMapping
    public ResponseEntity<List<WeatherSensorResponse>> getSensorStatistics(
            @RequestParam(value = "sensors", required = false) List<Long> sensors, //todo should these be arrays or lists?
            @RequestParam("stat") String stat,
            @RequestParam("metrics") List<String> metrics,
            @RequestParam(value = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) LocalDateTime endDate
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
