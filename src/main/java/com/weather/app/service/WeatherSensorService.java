package com.weather.app.service;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;

public interface WeatherSensorService {

    /**
     * Validates and maps the data and saves to the database
     *
     * @param id the sensor ID for the associated data
     * @param weatherSensorDataRequest the associated data points being saved for the sensor
     * @return the created data points for the sensor
     */
    WeatherSensorDataCreatedResponse createSensorData(Long id, WeatherSensorDataRequest weatherSensorDataRequest);
}
