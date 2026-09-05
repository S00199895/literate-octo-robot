package com.weather.app.service.impl;

import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.exception.InvalidSensorDataException;
import com.weather.app.mapper.WeatherSensorMapper;
import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import com.weather.app.service.WeatherSensorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.weather.app.common.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherSensorServiceImplTest {

    @Mock
    private WeatherSensorRepository weatherSensorRepositoryMock;

    @Mock
    private WeatherSensorMapper weatherSensorMapperMock;

    private WeatherSensorService cut;

    @BeforeEach
    void beforeEach() {
        cut = new WeatherSensorServiceImpl(weatherSensorRepositoryMock, weatherSensorMapperMock);
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(weatherSensorRepositoryMock, weatherSensorMapperMock);
    }

    @Test
    void test_createSensorData_returnsCreatedResponse() {

        final WeatherSensorEntity weatherSensorEntity = getWeatherSensorEntity();
        final WeatherSensorDataRequest weatherSensorDataRequest = getWeatherSensorDataRequest();
        final WeatherSensorEntity savedWeatherSensorEntity = getSavedWeatherSensorEntity();

        final WeatherSensorDataCreatedResponse expected = getWeatherSensorDataCreatedResponse();

        when(weatherSensorMapperMock.mapSensorDataRequestToEntity(SENSOR_ID_1, weatherSensorDataRequest)).thenReturn(weatherSensorEntity);
        when(weatherSensorRepositoryMock.save(weatherSensorEntity)).thenReturn(savedWeatherSensorEntity);
        when(weatherSensorMapperMock.mapSensorDataEntityToCreatedResponse(savedWeatherSensorEntity)).thenReturn(expected);

        final WeatherSensorDataCreatedResponse actual = cut.createSensorData(SENSOR_ID_1, weatherSensorDataRequest);

        assertThat(actual).isEqualTo(expected);

        verify(weatherSensorMapperMock).mapSensorDataRequestToEntity(SENSOR_ID_1, weatherSensorDataRequest);
        verify(weatherSensorRepositoryMock).save(weatherSensorEntity);
        verify(weatherSensorMapperMock).mapSensorDataEntityToCreatedResponse(savedWeatherSensorEntity);
    }

    @Test
    void test_createSensorData_failsValidationThrowsInvalidSensorDataException() {

        final WeatherSensorDataRequest invalidWeatherSensorDataRequest = getInvalidWeatherSensorDataRequest();

        assertThatThrownBy(() -> cut.createSensorData(null, invalidWeatherSensorDataRequest))
                .isInstanceOf(InvalidSensorDataException.class)
                .hasMessage(VALIDATION_ERRORS_STRING);
    }
}