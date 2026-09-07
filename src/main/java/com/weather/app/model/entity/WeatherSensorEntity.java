package com.weather.app.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Temporal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SENSOR_READING")
public class WeatherSensorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //
    private Long sensorId;
// todo see notes
//    private String statistic;
//
//    private BigDecimal value;
//
//    private LocalDateTime timestamp;

    BigDecimal temperature;

    BigDecimal humidity;

    BigDecimal windSpeed;

//    @Column(columnDefinition = "TIME")
    LocalDateTime readingTimestamp;
}
