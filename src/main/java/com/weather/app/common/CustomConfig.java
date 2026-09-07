package com.weather.app.common;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {
 //todo is this doing anything?
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper()
//                .configure(MapperFeature.REQUIRE_HANDLERS_FOR_JAVA8_TIMES, false)
//                .registerModule(new JavaTimeModule());
//    }
}
