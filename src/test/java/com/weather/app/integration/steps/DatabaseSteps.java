package com.weather.app.integration.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.weather.app.dto.WeatherSensorDataRequest;
import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import lombok.SneakyThrows;
import net.serenitybdd.annotations.Step;

import static com.weather.app.integration.steps.CommonSteps.readResourceFromFile;
import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseSteps {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @SneakyThrows
    @Step("Verify a new row was created in the database for sensor id {0}, request {1}")
    public void verifySensorRequestWasCreated(Long sensorId, String requestFile, WeatherSensorRepository weatherSensorRepository) {

        final String request = readResourceFromFile(requestFile);

        final WeatherSensorDataRequest weatherSensorDataRequest = OBJECT_MAPPER.readValue(request, WeatherSensorDataRequest.class);

        final WeatherSensorEntity actual = weatherSensorRepository.findBySensorId(sensorId).get();

        assertThat(actual.getHumidity()).isEqualTo(weatherSensorDataRequest.getHumidity()); //todo fails on the precision here? maybe not a big issue
        assertThat(actual.getTemperature()).isEqualTo(weatherSensorDataRequest.getTemperature());
        assertThat(actual.getWindSpeed()).isEqualTo(weatherSensorDataRequest.getWindSpeed());
        assertThat(actual.getReadingTimestamp()).isEqualTo(weatherSensorDataRequest.getTimestamp());
        assertThat(actual.getSensorId()).isEqualTo(sensorId);

    }
}
