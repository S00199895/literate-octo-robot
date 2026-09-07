package com.weather.app.mapper;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.model.entity.WeatherSensorEntity;
import org.springframework.stereotype.Component;

@Component
public class WeatherSensorMapperImpl implements WeatherSensorMapper {

    @Override
    public WeatherSensorEntity mapSensorDataRequestToEntity(final Long id, final WeatherSensorDataRequest request) {
        return WeatherSensorEntity.builder()
                .sensorId(id)
                .temperature(request.getTemperature())
                .humidity(request.getHumidity())
                .windSpeed(request.getWindSpeed())
                .readingTimestamp(request.getTimestamp())
                .build();
    }

    @Override
    public WeatherSensorDataCreatedResponse mapSensorDataEntityToCreatedResponse(final WeatherSensorEntity entity) {
        return WeatherSensorDataCreatedResponse.builder()
                .sensorId(entity.getSensorId())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .timestamp(entity.getReadingTimestamp())
                .build();
    }
}
