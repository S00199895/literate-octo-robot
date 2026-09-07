package com.weather.app.integration.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.weather.app.integration.WeatherSensorIT.baseUrl;
import static com.weather.app.integration.steps.CommonSteps.readResourceFromFile;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class WeatherSensorSteps {


    @Step("Make a POST request to the /weather endpoint with request {0}, and verify response is {1} with status code {2} for sensor {3}")
    public void setupAndVerifyPostWeatherEndpoint(String requestFile, String responseFile, HttpStatus httpStatus, Long sensorId) {

        final String requestBody = readResourceFromFile(requestFile);
        final String responseBody = readResourceFromFile(responseFile);

        Response response = given()
                .baseUri(baseUrl)
                .body(requestBody)
                .header("Content-Type", ContentType.JSON)
                .when()
                .post("/weather/" + sensorId).thenReturn();

        assertThat(response.getStatusCode()).isEqualTo(httpStatus.value());
        assertThat(response.getBody().prettyPrint()).isEqualTo(responseBody);
    }

    @Step("Make a GET request to the /weather endpoint and verify response is {3}, with status {4}")
    public void setupAndVerifyGetSensorStatisticsEndpoint(List<Long> sensors, String stat, List<String> metrics, String responseFile, HttpStatus httpStatus, LocalDateTime latestEndDate) {

        String responseBody = readResourceFromFile(responseFile);

        LocalDateTime latestStartDate = latestEndDate.minusDays(1L);

        responseBody = responseBody.replace("${startDateTimestamp}", latestStartDate.toString());
        responseBody = responseBody.replace("${endDateTimestamp}", latestEndDate.toString());

        Response response = given()
                .baseUri(baseUrl)
                .param("sensors", sensors)
                .param("stat", stat)
                .param("metrics", metrics)
                .param("startDate", latestStartDate.toString())
                .param("endDate", latestEndDate.toString())
                .when()
                .get("/weather").thenReturn();

        assertThat(response.getStatusCode()).isEqualTo(httpStatus.value());
        assertThat(response.getBody().prettyPrint()).isEqualTo(responseBody);
    }
}
