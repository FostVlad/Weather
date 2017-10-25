package com.goloveschenko.weather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoCities {
    @SerializedName("totalResultsCount")
    private int totalResultsCount;

    @SerializedName("geonames")
    private List<GeoCity> cities;

    public int getTotalResultsCount() {
        return totalResultsCount;
    }

    public List<GeoCity> getCities() {
        return cities;
    }
}
