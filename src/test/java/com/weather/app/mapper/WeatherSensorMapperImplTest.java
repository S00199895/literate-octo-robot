package com.weather.app.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeatherSensorMapperImplTest {

    private WeatherSensorMapper cut;

    @BeforeEach
    void beforeEach() {
        cut = new WeatherSensorMapperImpl();
    }

    @Test
    void test_mapSensorDataRequestToEntity_returnsWeatherSensorEntity() {
        //todo implement
    }

    @Test
    void test_mapSensorDataEntityToCreatedResponse_returnsWeatherSensorDataCreatedResponse() {
        //todo implement
    }
}