package com.weather.app.integration.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import lombok.SneakyThrows;
import net.serenitybdd.annotations.Step;

import java.time.LocalDateTime;

import static com.weather.app.integration.common.ITestConstants.*;
import static com.weather.app.integration.steps.CommonSteps.readResourceFromFile;
import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseSteps {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @SneakyThrows
    @Step("Verify a new row was created in the database for sensor id {0}, request {1}")
    public void verifySensorRequestWasCreated(Long sensorId, String requestFile, WeatherSensorRepository weatherSensorRepository) {

        final String request = readResourceFromFile(requestFile);

        final WeatherSensorDataRequest weatherSensorDataRequest = OBJECT_MAPPER.readValue(request, WeatherSensorDataRequest.class);

        final WeatherSensorEntity actual = weatherSensorRepository.findBySensorId(sensorId).get();

        assertThat(actual.getHumidity()).isEqualTo(weatherSensorDataRequest.getHumidity());
        assertThat(actual.getTemperature()).isEqualTo(weatherSensorDataRequest.getTemperature());
        assertThat(actual.getWindSpeed()).isEqualTo(weatherSensorDataRequest.getWindSpeed());
        assertThat(actual.getReadingTimestamp()).isEqualTo(weatherSensorDataRequest.getTimestamp());
        assertThat(actual.getSensorId()).isEqualTo(sensorId);

    }

    @Step("Verify the database is still empty")
    public void verifyDatabaseWasNotUpdated(WeatherSensorRepository weatherSensorRepository) {

        assertThat(weatherSensorRepository.findAll()).isEmpty();
    }

    public void seedLatestDataToQuery(WeatherSensorRepository weatherSensorRepository, LocalDateTime latestEndDate) {

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(SENSOR_ID_1)
                        .temperature(TEMPERATURE_1)
                        .humidity(HUMIDITY_1)
                        .windSpeed(WIND_SPEED_1)
                        .readingTimestamp(latestEndDate)
                        .build()
        );

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(SENSOR_ID_1)
                        .temperature(TEMPERATURE_2)
                        .humidity(HUMIDITY_2)
                        .windSpeed(WIND_SPEED_2)
                        .readingTimestamp(latestEndDate)
                        .build()
        );

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(SENSOR_ID_2)
                        .temperature(TEMPERATURE_3)
                        .humidity(HUMIDITY_3)
                        .windSpeed(WIND_SPEED_3)
                        .readingTimestamp(latestEndDate)
                        .build()
        );

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(SENSOR_ID_2)
                        .temperature(TEMPERATURE_4)
                        .humidity(HUMIDITY_4)
                        .windSpeed(WIND_SPEED_4)
                        .readingTimestamp(latestEndDate)
                        .build()
        );
    }
}
