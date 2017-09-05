package com.goloveschenko.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class Astro {
    @SerializedName("sunrise")
    private String sunrise;
    @SerializedName("sunset")
    private String sunset;
    @SerializedName("moonrise")
    private String moonrise;
    @SerializedName("moonset")
    private String moonset;

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public String getMoonset() {
        return moonset;
    }
}
