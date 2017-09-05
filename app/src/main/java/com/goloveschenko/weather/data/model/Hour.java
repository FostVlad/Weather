package com.goloveschenko.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class Hour {
    @SerializedName("time")
    private String time;
    @SerializedName("temp_c")
    private double tempC;
    @SerializedName("is_day")
    private int isDay;
    @SerializedName("condition")
    private Condition condition;
    @SerializedName("wind_kph")
    private double windKph;
    @SerializedName("pressure_mb")
    private int pressureMb;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("cloud")
    private int cloud;
    @SerializedName("feelslike_c")
    private double feelsLike;
    @SerializedName("will_it_rain")
    private int willItRain;
    @SerializedName("chance_of_rain")
    private int chanceOfRain;
    @SerializedName("will_it_snow")
    private int willItSnow;
    @SerializedName("chance_of_snow")
    private int chanceOfSnow;
    @SerializedName("vis_km")
    private double visKm;

    public String getTime() {
        return time;
    }

    public double getTempC() {
        return tempC;
    }

    public int getIsDay() {
        return isDay;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getWindKph() {
        return windKph;
    }

    public int getPressureMb() {
        return pressureMb;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public int getWillItRain() {
        return willItRain;
    }

    public int getChanceOfRain() {
        return chanceOfRain;
    }

    public int getWillItSnow() {
        return willItSnow;
    }

    public int getChanceOfSnow() {
        return chanceOfSnow;
    }

    public double getVisKm() {
        return visKm;
    }
}
