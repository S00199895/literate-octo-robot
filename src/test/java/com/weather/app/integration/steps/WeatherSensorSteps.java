package com.weather.app.integration.steps;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import org.springframework.http.HttpStatus;

import static com.weather.app.integration.WeatherSensorIT.baseUrl;
import static com.weather.app.integration.steps.CommonSteps.readResourceFromFile;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class WeatherSensorSteps {


    @Step("Make a POST request to the /weather endpoint with request {0}")
    public void setupPostWeatherEndpoint(String requestFile, String responseFile, Long sensorId) {

        final String requestBody = readResourceFromFile(requestFile);
        final String responseBody = readResourceFromFile(responseFile);

        Response response = given()
                .baseUri(baseUrl)
                .body(requestBody)
                .header("Content-Type", ContentType.JSON)
                .when()
                .post("/weather/" + sensorId).thenReturn();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getBody().prettyPrint()).isEqualTo(responseBody);
    }
}
