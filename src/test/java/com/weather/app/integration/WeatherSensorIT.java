package com.weather.app.integration;

import com.weather.app.WeatherApplication;
import com.weather.app.integration.steps.DatabaseSteps;
import com.weather.app.integration.steps.WeatherSensorSteps;
import com.weather.app.model.WeatherSensorRepository;
import jakarta.annotation.PostConstruct;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.Map;

import static com.weather.app.integration.common.ITestConstants.*;
import static java.util.Collections.emptyList;

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

    @AfterAll
    static void afterAll() {
        POSTGRES_CONTAINER.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    }

    @Test
    @DisplayName("Sensor Statistic data request is posted to the application, gets saved in the database and returns a 201 Created")
    void test_createSensorStatisticReturns201Created() {

        weatherSensorSteps.setupAndVerifyPostWeatherEndpoint(
                POST_SENSOR_DATA_REQUEST_CREATED_1,
                POST_SENSOR_DATA_REQUEST_CREATED_RESPONSE_1,
                HttpStatus.CREATED,
                SENSOR_ID_1);

        databaseSteps.verifySensorRequestWasCreated(SENSOR_ID_1,
                POST_SENSOR_DATA_REQUEST_CREATED_1,
                weatherSensorRepository);
    }

    @Test
    @DisplayName("Sensor Statistic data request is posted to the application, fails validation and returns a 400 Bad Request with errors")
    void test_createSensorStatisticReturns400BadRequest() {

        weatherSensorSteps.setupAndVerifyPostWeatherEndpoint(
                POST_SENSOR_DATA_REQUEST_INVALID_REQUEST_1,
                POST_SENSOR_DATA_REQUEST_BAD_REQUEST_RESPONSE_1,
                HttpStatus.BAD_REQUEST,
                SENSOR_ID_2
        );

        databaseSteps.verifyDatabaseWasNotUpdated(weatherSensorRepository);
    }

    @Test
    @DisplayName("Querying get sensor statistics returns all sensors' data with the latest data")
    void test_getSensorStatisticsReturns200OKWithLatestData() {

        LocalDateTime latestEndDate = LocalDateTime.now().minusDays(1);

        databaseSteps.seedLatestDataToQuery(weatherSensorRepository, latestEndDate);

        weatherSensorSteps.setupAndVerifyGetSensorStatisticsEndpoint(
                emptyList(),
                AVERAGE_STAT,
                ALL_METRICS,
                GET_SENSOR_STATISTIC_OK_RESPONSE_1,
                HttpStatus.OK,
                latestEndDate
        );
        /*
        * figure out how we're gonna manage the times here
        * */
    }
    /*
     * 400 path for this
     * happy path for the query
     * docs
     * */
}
