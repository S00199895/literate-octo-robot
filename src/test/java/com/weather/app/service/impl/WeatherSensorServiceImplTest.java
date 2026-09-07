package com.weather.app.service.impl;

import com.weather.app.dto.ValidatedQueryResult;
import com.weather.app.dto.WeatherSensorDataCreatedResponse;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.dto.WeatherSensorStatisticResponse;
import com.weather.app.exception.InvalidSensorDataException;
import com.weather.app.mapper.WeatherSensorMapper;
import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import com.weather.app.service.StatService;
import com.weather.app.service.ValidationService;
import com.weather.app.service.WeatherSensorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.weather.app.common.TestConstants.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherSensorServiceImplTest {

    @Mock
    private WeatherSensorRepository weatherSensorRepositoryMock;

    @Mock
    private WeatherSensorMapper weatherSensorMapperMock;

    @Mock
    private StatService statServiceMock;

    @Mock
    private ValidationService validationServiceMock;

    private WeatherSensorService cut;

    @BeforeEach
    void beforeEach() {
        cut = new WeatherSensorServiceImpl(weatherSensorRepositoryMock, weatherSensorMapperMock, statServiceMock, validationServiceMock);
    }

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(weatherSensorRepositoryMock, weatherSensorMapperMock, statServiceMock, validationServiceMock);
    }

    @Test
    void test_getSensorStatistics_returnsWeatherSensorResponseList() {

        final ValidatedQueryResult validatedQueryResult = getValidatedQueryResult();

        when(validationServiceMock.validateSensorQueryParams(MIN_STAT, ALL_METRICS, START_DATE_1, END_DATE_1)).thenReturn(validatedQueryResult);
        when(weatherSensorRepositoryMock.findSensorMetricsBySensorIdsBetweenDates(SENSOR_IDS, START_DATE_1, END_DATE_1)).thenReturn(SENSOR_METRICS);

        when(statServiceMock.calculateStat(TEMPERATURE_2_LIST, MIN_STAT, TEMPERATURE_METRIC)).thenReturn(STAT_RESPONSE_TEMPERATURE_2);
        when(statServiceMock.calculateStat(HUMIDITY_2_LIST, MIN_STAT, HUMIDITY_METRIC)).thenReturn(STAT_RESPONSE_HUMIDITY_2);
        when(statServiceMock.calculateStat(WIND_SPEED_2_LIST, MIN_STAT, WIND_SPEED_METRIC)).thenReturn(STAT_RESPONSE_WIND_SPEED_2);

        when(statServiceMock.calculateStat(TEMPERATURE_3_LIST, MIN_STAT, TEMPERATURE_METRIC)).thenReturn(STAT_RESPONSE_TEMPERATURE_3);
        when(statServiceMock.calculateStat(HUMIDITY_3_LIST, MIN_STAT, HUMIDITY_METRIC)).thenReturn(STAT_RESPONSE_HUMIDITY_3);
        when(statServiceMock.calculateStat(WIND_SPEED_3_LIST, MIN_STAT, WIND_SPEED_METRIC)).thenReturn(STAT_RESPONSE_WIND_SPEED_3);

        final List<WeatherSensorStatisticResponse> actual = cut.getSensorStatistics(SENSOR_IDS_LIST, MIN_STAT, ALL_METRICS, START_DATE_1, END_DATE_1);

        assertThat(actual).isEqualTo(WEATHER_SENSOR_RESPONSES);

        verify(validationServiceMock).validateSensorQueryParams(MIN_STAT, ALL_METRICS, START_DATE_1, END_DATE_1);
        verify(weatherSensorRepositoryMock).findSensorMetricsBySensorIdsBetweenDates(SENSOR_IDS, START_DATE_1, END_DATE_1);

        verify(statServiceMock).calculateStat(TEMPERATURE_2_LIST, MIN_STAT, TEMPERATURE_METRIC);
        verify(statServiceMock).calculateStat(HUMIDITY_2_LIST, MIN_STAT, HUMIDITY_METRIC);
        verify(statServiceMock).calculateStat(WIND_SPEED_2_LIST, MIN_STAT, WIND_SPEED_METRIC);

        verify(statServiceMock).calculateStat(TEMPERATURE_3_LIST, MIN_STAT, TEMPERATURE_METRIC);
        verify(statServiceMock).calculateStat(HUMIDITY_3_LIST, MIN_STAT, HUMIDITY_METRIC);
        verify(statServiceMock).calculateStat(WIND_SPEED_3_LIST, MIN_STAT, WIND_SPEED_METRIC);
    }

    @Test
    void test_getSensorStatistics_failsOnValidationAndThrowsInvalidSensorDataException() {
        // todo implement
    }

    @Test
    void test_createSensorData_returnsCreatedResponse() {

        final WeatherSensorEntity weatherSensorEntity = getWeatherSensorEntity();
        final WeatherSensorDataRequest weatherSensorDataRequest = getWeatherSensorDataRequest();
        final WeatherSensorEntity savedWeatherSensorEntity = getSavedWeatherSensorEntity();

        final WeatherSensorDataCreatedResponse expected = getWeatherSensorDataCreatedResponse();

        when(validationServiceMock.validateSensorDataRequest(SENSOR_ID_1, weatherSensorDataRequest)).thenReturn(emptyList());
        when(weatherSensorMapperMock.mapSensorDataRequestToEntity(SENSOR_ID_1, weatherSensorDataRequest)).thenReturn(weatherSensorEntity);
        when(weatherSensorRepositoryMock.save(weatherSensorEntity)).thenReturn(savedWeatherSensorEntity);
        when(weatherSensorMapperMock.mapSensorDataEntityToCreatedResponse(savedWeatherSensorEntity)).thenReturn(expected);

        final WeatherSensorDataCreatedResponse actual = cut.createSensorData(SENSOR_ID_1, weatherSensorDataRequest);

        assertThat(actual).isEqualTo(expected);

        verify(validationServiceMock).validateSensorDataRequest(SENSOR_ID_1, weatherSensorDataRequest);
        verify(weatherSensorMapperMock).mapSensorDataRequestToEntity(SENSOR_ID_1, weatherSensorDataRequest);
        verify(weatherSensorRepositoryMock).save(weatherSensorEntity);
        verify(weatherSensorMapperMock).mapSensorDataEntityToCreatedResponse(savedWeatherSensorEntity);
    }

    @Test
    void test_createSensorData_failsValidationThrowsInvalidSensorDataException() {

        final WeatherSensorDataRequest invalidWeatherSensorDataRequest = getInvalidWeatherSensorDataRequest();

        when(validationServiceMock.validateSensorDataRequest(null, invalidWeatherSensorDataRequest)).thenReturn(VALIDATION_ERROR_LIST);

        assertThatThrownBy(() -> cut.createSensorData(null, invalidWeatherSensorDataRequest))
                .isInstanceOf(InvalidSensorDataException.class)
                .hasMessage(VALIDATION_ERRORS_STRING);

        verify(validationServiceMock).validateSensorDataRequest(null, invalidWeatherSensorDataRequest);
    }
}