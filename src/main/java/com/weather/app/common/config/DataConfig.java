package com.weather.app.common.config;

import com.weather.app.model.WeatherSensorRepository;
import com.weather.app.model.entity.WeatherSensorEntity;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Configuration
@Profile("!it")
public class DataConfig {

    @Autowired
    WeatherSensorRepository weatherSensorRepository;

    @PostConstruct
    void seedData() {

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(1006L)
                        .temperature(new BigDecimal("20.00"))
                        .humidity(new BigDecimal("10.00"))
                        .windSpeed(new BigDecimal("15.00"))
                        .readingTimestamp(LocalDateTime.now().minusDays(2L))
                        .build()
        );

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(1008L)
                        .temperature(new BigDecimal("10.00"))
                        .humidity(new BigDecimal("1.00"))
                        .windSpeed(new BigDecimal("11.00"))
                        .readingTimestamp(LocalDateTime.now().minusDays(2L))
                        .build()
        );

        weatherSensorRepository.save(
                WeatherSensorEntity.builder()
                        .sensorId(1008L)
                        .temperature(new BigDecimal("25.00"))
                        .humidity(new BigDecimal("40.00"))
                        .windSpeed(new BigDecimal("50.00"))
                        .readingTimestamp(LocalDateTime.now().minusDays(2L))
                        .build()
        );

        log.info("Init data seeded");
    }
}
