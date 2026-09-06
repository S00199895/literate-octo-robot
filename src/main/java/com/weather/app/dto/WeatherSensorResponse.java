package com.weather.app.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class WeatherSensorResponse { //todo good name?

    Long id;

    List<StatResponse> statistics;

    LocalDateTime startDate;

    LocalDateTime endDate;
}
