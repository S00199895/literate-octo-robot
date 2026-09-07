package com.weather.app.service.impl;

import com.weather.app.dto.ValidatedQueryResult;
import com.weather.app.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Clock;
import java.time.ZoneId;

import static com.weather.app.common.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

class ValidationServiceImplTest {

    private ValidationService cut;

    private Clock clock = Clock.fixed(FIXED_INSTANT, ZoneId.systemDefault());

    @BeforeEach
    void beforeEach() {
        cut = new ValidationServiceImpl();
        ReflectionTestUtils.setField(cut, "clock", clock);
    }

    @Test
    void test_validateSensorQueryParams_returnsValidatedQueryResultForDefaultDateAndErrors() {

        final ValidatedQueryResult expected = getValidatedQueryResultWithErrors();

        final ValidatedQueryResult actual = cut.validateSensorQueryParams(INVALID_STAT, INVALID_METRICS, null, null);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void test_validateSensorDataRequest_returnsValidationErrorsList() {
        // todo implement
    }
}