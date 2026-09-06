package com.weather.app.integration.steps;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class CommonSteps {

    @SneakyThrows
    public static String readResourceFromFile(String path) {
        return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
    }
}
