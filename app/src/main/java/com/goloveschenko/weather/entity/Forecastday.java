package com.goloveschenko.weather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecastday {
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Day day;
    @SerializedName("astro")
    private Astro astro;
    @SerializedName("hour")
    private List<Hour> hour = null;

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    public Astro getAstro() {
        return astro;
    }

    public List<Hour> getHour() {
        return hour;
    }
}


