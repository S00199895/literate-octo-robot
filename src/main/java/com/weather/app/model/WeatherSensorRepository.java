package com.weather.app.model;

import com.weather.app.dto.SensorMetric;
import com.weather.app.model.entity.WeatherSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherSensorRepository extends JpaRepository<WeatherSensorEntity, Long> {

    //todo named methods in here?
    /*
     * this is a bit awkward because we want the
     * different stats as functions, so do we just want to return all the sensors and then worry about the results?
     *
     *
     * to be able to query multiple metrics for the same stat, you'd have to be constructing your query as
     * you go along. which i think is quite messy and not deterministic enough
     *
     * lets just return the sensor ids, the metrics, and then do the fucntion in a stream
     *
     * so how are we going to do that. returns a list of dto sensor metric
     *
     * if we cant pass the columns we want in here, we'll have to select them all each time
     * and then inside the stat loop, you'll have to also loop through the metrics you want
     *
     * StatResponse will have to contain all the fields temperature, humidity, wind speed
     * inside the case/if, loop through the fields
     *  how will we do this though?
     * have a map of string, bigdecimal and do a get for each metric
     *
     * figure this out
     * then other validation on the fields there PLUS THE DATES LOGIC
     * then tests and IT
     *
     * problem is we have to get all now here
     *
     * sen
     * */
    @Query(nativeQuery = true, value = """
            SELECT sensor_id, temperature, humidity, wind_speed/*:metrics*/ 
            FROM SENSOR_READING
            WHERE READING_TIMESTAMP between :startDate AND :endDate
            """)
    List<SensorMetric> findAllSensorsMetricsBetweenDates(
            @Param("startDate") LocalDateTime startDate, //we'll sort out the nulls in these in code and then pass certain values
            @Param("endDate") LocalDateTime endDate
    );

    /*
     * we'll have another funciton then to add sensor ids to the where
     * */
    @Query(nativeQuery = true, value = """
            SELECT sensor_id, temperature, humidity, wind_speed
            FROM SENSOR_READING
            WHERE SENSOR_ID IN (:sensorIds)
            AND READING_TIMESTAMP between :startDate AND :endDate
            """)
    List<SensorMetric> findSensorMetricsBySensorIdsBetweenDates(
            @Param("sensorIds") Long[] sensorIds, //todo does this want to be a string too?
            @Param("startDate") LocalDateTime startDate, //we'll sort out the nulls in these in code and then pass certain values
            @Param("endDate") LocalDateTime endDate
    );
}
