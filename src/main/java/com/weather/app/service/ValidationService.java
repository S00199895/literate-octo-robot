package com.weather.app.service;

import com.weather.app.dto.ValidatedQueryResult;
import com.weather.app.dto.ValidationError;
import com.weather.app.dto.WeatherSensorDataRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface ValidationService {

    /**
     *
     * @param stat
     * @param metrics
     * @param startDate
     * @param endDate
     * @return
     */
    ValidatedQueryResult validateSensorQueryParams(String stat, List<String> metrics, LocalDateTime startDate, LocalDateTime endDate);

    /**
     *
     * @param id
     * @param weatherSensorDataRequest
     * @return
     */
    List<ValidationError> validateSensorDataRequest(Long id, WeatherSensorDataRequest weatherSensorDataRequest);
}
