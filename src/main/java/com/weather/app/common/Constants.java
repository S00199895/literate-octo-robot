package com.weather.app.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String MIN_STAT = "min";

    public static final String MAX_STAT = "max";

    public static final String AVERAGE_STAT = "average";

    public static final String SUM_STAT = "sum";

    public static final String TEMPERATURE_METRIC = "temperature";
    public static final String HUMIDITY_METRIC = "humidity";
    public static final String WIND_SPEED_METRIC = "windSpeed";

    public static final String[] METRICS_ALLOWED = new String[]{TEMPERATURE_METRIC, HUMIDITY_METRIC, WIND_SPEED_METRIC};

    public static final String[] STATS_ALLOWED = new String[]{MIN_STAT, MAX_STAT, AVERAGE_STAT, SUM_STAT};
}
