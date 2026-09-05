package com.weather.app.service.impl;

import com.weather.app.dto.ValidationError;
import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.exception.InvalidSensorDataException;
import com.weather.app.mapper.WeatherSensorMapper;
import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import com.weather.app.service.WeatherSensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WeatherSensorServiceImpl implements WeatherSensorService {

    private final WeatherSensorRepository weatherSensorRepository;

    private final WeatherSensorMapper weatherSensorMapper;


    @Override
    public WeatherSensorDataCreatedResponse createSensorData(Long id, WeatherSensorDataRequest weatherSensorDataRequest) {

        //todo do you then want to be worrying about different sensors existing?

        final List<ValidationError> validationErrors = validateSensorDataRequest(id, weatherSensorDataRequest);
        if (!validationErrors.isEmpty()) {
            throw new InvalidSensorDataException(validationErrors);
        }

        final WeatherSensorEntity weatherSensorEntity = weatherSensorMapper.mapSensorDataRequestToEntity(id, weatherSensorDataRequest);

        final WeatherSensorEntity createdWeatherSensorEntity = weatherSensorRepository.save(weatherSensorEntity);

        return weatherSensorMapper.mapSensorDataEntityToCreatedResponse(createdWeatherSensorEntity);
    }

    private List<ValidationError> validateSensorDataRequest(Long id, WeatherSensorDataRequest weatherSensorDataRequest) {

        List<ValidationError> validationErrors = new ArrayList<>();

        if (isNull(id)) {
            validationErrors.add(ValidationError.builder()
                    .field("id")
                    .message("sensor id cannot be null")
                    .build());
        }

        if (isNull(weatherSensorDataRequest.getWindSpeed())
                || weatherSensorDataRequest.getWindSpeed().compareTo(BigDecimal.ZERO) < 0) {
            validationErrors.add(ValidationError.builder()
                    .field("windSpeed")
                    .message("Invalid value for windSpeed: " + weatherSensorDataRequest.getWindSpeed())
                    .build());
        }

        if (isNull(weatherSensorDataRequest.getHumidity())
                || weatherSensorDataRequest.getHumidity().compareTo(BigDecimal.ZERO) < 0) {
            validationErrors.add(ValidationError.builder()
                    .field("humidity")
                    .message("Invalid value for humidity: " + weatherSensorDataRequest.getHumidity())
                    .build());
        }

        return validationErrors;
    }
}
