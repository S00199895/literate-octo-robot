package com.weather.app.mapper;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.model.entity.WeatherSensorEntity;

public interface WeatherSensorMapper {

    /**
     *
     * @param id the sensor ID to be mapped
     * @param request the sensor's data points to be mapped
     * @return the mapped WeatherSensorEntity
     */
    WeatherSensorEntity mapSensorDataRequestToEntity(Long id, WeatherSensorDataRequest request);

    /**
     *
     * @param request the sensor's data points to be mapped
     * @return the mapped WeatherSensorDataCreatedResponse
     */
    WeatherSensorDataCreatedResponse mapSensorDataEntityToCreatedResponse(WeatherSensorEntity request);
}
