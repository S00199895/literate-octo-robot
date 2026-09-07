package com.weather.app.integration.common;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.weather.app.integration.WeatherSensorIT.POSTGRES_CONTAINER;
//
//@Testcontainers
//public class TestContainersExtension implements BeforeAllCallback {
//
//    public static AtomicBoolean INITIALIZED = new AtomicBoolean();
//
//    public static Network network = Network.newNetwork();
//
//    @Override
//    public void beforeAll(ExtensionContext context) {
//        if (!INITIALIZED.getAndSet(true)) {
//            startContainers(network);
//        }
//    }
//
//    private static void startContainers(Network network) {
//
//        POSTGRES_CONTAINER = new PostgreSQLContainer("postgres:14-alpine")
//                .withExposedPorts(5432)
//                .withNetwork(network)
//                .withEnv("POSTGRES_DB", "sensor")
//                .withEnv("POSTGRES_USER", "user")
//                .withEnv("POSTGRES_PASSWORD", "user")
//                .waitingFor(Wait.forLogMessage(".*ready to accept connections.*\\n", 1));
///*
//*       POSTGRES_DB: sensor
//      POSTGRES_USER: user
//      POSTGRES_PASSWORD: user
//* */
//        POSTGRES_CONTAINER.start();
//
////        System.setProperty("DB_DEFAULT_HOST", POSTGRES_CONTAINER.getMappedPort(5432).toString());
////        System.setProperty("DB_DEFAULT_PORT", "5432");
//        System.out.println();
//
//        //todo ports?
//
//    }
//}
