package com.weather.app.integration.common;

import java.math.BigDecimal;
import java.util.List;

public class ITestConstants {

    public static final String RESOURCES_DIR = "src/test/resources/";

    public static final String JSON_EXT = ".json";

    public static final String REQUEST_DIR = RESOURCES_DIR + "request/";

    public static final String RESPONSE_DIR = RESOURCES_DIR + "response/";

    public static final String POST_SENSOR_DATA_REQUEST_CREATED_1 = REQUEST_DIR + "post-sensor-data-request-created-1";

    public static final String POST_SENSOR_DATA_REQUEST_INVALID_REQUEST_1 = REQUEST_DIR + "post-sensor-data-request-invalid-1";

    public static final String POST_SENSOR_DATA_REQUEST_CREATED_RESPONSE_1 = RESPONSE_DIR + "post-sensor-data-request-created-response-1";

    public static final String POST_SENSOR_DATA_REQUEST_BAD_REQUEST_RESPONSE_1 = RESPONSE_DIR + "post-sensor-data-request-bad-request-response-1";

    public static final String GET_SENSOR_STATISTIC_OK_RESPONSE_1 = RESPONSE_DIR + "get-sensor-statistic-response-ok-1";

    public static final Long SENSOR_ID_1 = 10032L;

    public static final Long SENSOR_ID_2 = 71237L;

    public static final BigDecimal TEMPERATURE_1 = new BigDecimal("1.00");
    public static final BigDecimal HUMIDITY_1 = new BigDecimal("65.00");
    public static final BigDecimal WIND_SPEED_1 = new BigDecimal("20.00");

    public static final BigDecimal TEMPERATURE_2 = new BigDecimal("5.00");
    public static final BigDecimal HUMIDITY_2 = new BigDecimal("32.00");
    public static final BigDecimal WIND_SPEED_2 = new BigDecimal("10.00");

    public static final BigDecimal TEMPERATURE_3 = new BigDecimal("40.00");
    public static final BigDecimal HUMIDITY_3 = new BigDecimal("25.00");
    public static final BigDecimal WIND_SPEED_3 = new BigDecimal("25.00");

    public static final BigDecimal TEMPERATURE_4 = new BigDecimal("10.00");
    public static final BigDecimal HUMIDITY_4 = new BigDecimal("20.00");
    public static final BigDecimal WIND_SPEED_4 = new BigDecimal("5.00");

    public static final String AVERAGE_STAT = "average";

    public static final String TEMPERATURE_METRIC = "temperature";

    public static final String HUMIDITY_METRIC = "humidity";

    public static final String WIND_SPEED_METRIC = "windSpeed";

    public static final List<String> ALL_METRICS = List.of(TEMPERATURE_METRIC, HUMIDITY_METRIC, WIND_SPEED_METRIC);
}
