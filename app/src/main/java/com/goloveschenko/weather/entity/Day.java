package com.goloveschenko.weather.entity;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("maxtemp_c")
    private double maxTempC;
    @SerializedName("mintemp_c")
    private double minTempC;
    @SerializedName("avgtemp_c")
    private double avgTempC;
    @SerializedName("maxwind_kph")
    private double maxWindKph;
    @SerializedName("avgvis_km")
    private double avgVisKm;
    @SerializedName("avghumidity")
    private int avgHumidity;
    @SerializedName("condition")
    private Condition condition;

    public double getMaxTempC() {
        return maxTempC;
    }

    public double getMinTempC() {
        return minTempC;
    }

    public double getAvgTempC() {
        return avgTempC;
    }

    public double getMaxWindKph() {
        return maxWindKph;
    }

    public double getAvgVisKm() {
        return avgVisKm;
    }

    public int getAvgHumidity() {
        return avgHumidity;
    }

    public Condition getCondition() {
        return condition;
    }
}
