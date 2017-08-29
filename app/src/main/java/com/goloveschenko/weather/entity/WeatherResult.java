package com.goloveschenko.weather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResult {
    public class Forecast {
        @SerializedName("forecastday")
        private List<Forecastday> forecastday = null;

        public List<Forecastday> getForecastday() {
            return forecastday;
        }
    }

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
