package com.weather.app.integration;

import com.weather.app.WeatherApplication;
import com.weather.app.integration.steps.DatabaseSteps;
import com.weather.app.integration.steps.WeatherSensorSteps;
import com.weather.app.model.WeatherSensorRepository;
import jakarta.annotation.PostConstruct;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Map;

import static com.weather.app.integration.common.ITestConstants.SENSOR_ID_1;

@ActiveProfiles("it")
@ExtendWith(SerenityJUnit5Extension.class)
@SpringBootTest(classes = WeatherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherSensorIT {

    @Steps
    private WeatherSensorSteps weatherSensorSteps;

    @Steps
    private DatabaseSteps databaseSteps;

    @Autowired
    private WeatherSensorRepository weatherSensorRepository;

    @LocalServerPort
    private int port;

    public static String baseUrl;

    @PostConstruct
    public void setDefaultPort() {
        SerenityRest.setDefaultPort(port);
        baseUrl = "http://localhost:" + port;
    }

    public static PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer(
            "postgres:14-alpine").withEnv(Map.of(
            "POSTGRES_DB", "sensor",
            "POSTGRES_USER", "user",
            "POSTGRES_PASSWORD", "user"
    ));

    @BeforeAll
    static void beforeAll() {
        POSTGRES_CONTAINER.start();
    }

    @AfterEach
    void afterEach() {
        weatherSensorRepository.deleteAll();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    }

    @Test
    @DisplayName("Sensor Statistic data requested is posted to the application, gets saved in the database and returns a 201 Created")
    void test_createSensorStatisticReturns201Created() {
        //todo
        weatherSensorSteps.setupPostWeatherEndpoint(
                "src/test/resources/request/post-sensor-data-request-created-1.json",
                "src/test/resources/response/post-sensor-data-request-created-response-1.json",
                SENSOR_ID_1);

        databaseSteps.verifySensorRequestWasCreated(SENSOR_ID_1,
                "src/test/resources/request/post-sensor-data-request-created-1.json",
                weatherSensorRepository);

    }

    /*
    * 400 path for this
    * happy path for the query
    * docs
    * */
}
