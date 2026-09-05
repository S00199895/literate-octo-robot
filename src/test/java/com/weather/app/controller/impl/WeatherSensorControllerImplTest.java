package com.weather.app.controller.impl;

import com.weather.app.controller.WeatherSensorController;
import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.service.WeatherSensorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.weather.app.common.TestConstants.*;
import static com.weather.app.common.TestConstants.getWeatherSensorDataCreatedResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherSensorControllerImplTest {

    @Mock
    private WeatherSensorService weatherSensorServiceMock;

    private WeatherSensorController cut;

    @BeforeEach
    void beforeEach() {
        cut = new WeatherSensorControllerImpl(weatherSensorServiceMock);
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(weatherSensorServiceMock);
    }

    @Test
    void test_createSensorStatistic_returns201CreatedWithTheCreatedResponse() {

        final WeatherSensorDataRequest weatherSensorDataRequest = getWeatherSensorDataRequest();

        final WeatherSensorDataCreatedResponse expected = getWeatherSensorDataCreatedResponse();

        when(weatherSensorServiceMock.createSensorData(SENSOR_ID_1, weatherSensorDataRequest)).thenReturn(expected);

        final ResponseEntity<WeatherSensorDataCreatedResponse> actual = cut.createSensorStatistic(SENSOR_ID_1, weatherSensorDataRequest);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody()).isEqualTo(expected);

        verify(weatherSensorServiceMock).createSensorData(SENSOR_ID_1, weatherSensorDataRequest);
    }
}