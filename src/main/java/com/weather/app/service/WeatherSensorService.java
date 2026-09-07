package com.weather.app.service;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.dto.WeatherSensorStatisticResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherSensorService {

    /**
     *
     * @param sensors a list of sensor IDs
     * @param stat the requested statistic to return
     * @param metrics the request metrics
     * @param startDate
     * @param endDate
     * @return the requested statistics for the dates and sensors specified
     */
    List<WeatherSensorStatisticResponse> getSensorStatistics(List<Long> sensors, String stat, List<String> metrics, LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Validates and maps the data and saves to the database
     *
     * @param id the sensor ID for the associated data
     * @param weatherSensorDataRequest the associated data points being saved for the sensor
     * @return the created data points for the sensor
     */
    WeatherSensorDataCreatedResponse createSensorData(Long id, WeatherSensorDataRequest weatherSensorDataRequest);
}
