package com.goloveschenko.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class ForecastWeather {
    @SerializedName("location")
    private Location location;
    @SerializedName("current")
    private Current current;
    @SerializedName("forecast")
    private Forecast forecast;

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }
}
