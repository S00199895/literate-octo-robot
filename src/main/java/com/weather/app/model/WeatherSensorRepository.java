package com.weather.app.model;

import com.weather.app.model.entity.WeatherSensorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSensorRepository extends JpaRepository<WeatherSensorEntity, Long> {

    //todo named methods in here?
}
