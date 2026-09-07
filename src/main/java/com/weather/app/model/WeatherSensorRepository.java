package com.weather.app.model;

import com.weather.app.dto.SensorMetric;
import com.weather.app.model.entity.WeatherSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherSensorRepository extends JpaRepository<WeatherSensorEntity, Long> {

    @Query(nativeQuery = true, value = """
            SELECT sensor_id, temperature, humidity, wind_speed
            FROM SENSOR_READING
            WHERE READING_TIMESTAMP between :startDate AND :endDate
            """)
    List<SensorMetric> findAllSensorsMetricsBetweenDates(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query(nativeQuery = true, value = """
            SELECT sensor_id, temperature, humidity, wind_speed
            FROM SENSOR_READING
            WHERE SENSOR_ID IN (:sensorIds)
            AND READING_TIMESTAMP between :startDate AND :endDate
            """)
    List<SensorMetric> findSensorMetricsBySensorIdsBetweenDates(
            @Param("sensorIds") Long[] sensorIds,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    Optional<WeatherSensorEntity> findBySensorId(Long sensorId);
}
