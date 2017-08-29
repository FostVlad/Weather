package com.goloveschenko.weather.entity;

import com.google.gson.annotations.SerializedName;

public class Current {
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("temp_c")
    private double temp;
    @SerializedName("is_day")
    private int isDay;
    @SerializedName("condition")
    private Condition condition;
    @SerializedName("pressure_mb")
    private double pressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("cloud")
    private int cloud;
    @SerializedName("feelslike_c")
    private double feelsLike;
    @SerializedName("vis_km")
    private double visKm;

    public String getLastUpdated() {
        return lastUpdated;
    }

    public double getTemp() {
        return temp;
    }

    public int getIsDay() {
        return isDay;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getFeelslike() {
        return feelsLike;
    }

    public double getVisKm() {
        return visKm;
    }
}
