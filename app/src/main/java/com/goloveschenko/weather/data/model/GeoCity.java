package com.goloveschenko.weather.data.model;

import com.google.gson.annotations.SerializedName;

public class GeoCity {
    @SerializedName("adminCode1")
    private String adminCode1;
    @SerializedName("lng")
    private String lng;
    @SerializedName("lat")
    private String lat;
    @SerializedName("geonameId")
    private long geonameId;
    @SerializedName("toponymName")
    private String toponymName;
    @SerializedName("countryId")
    private String countryId;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("name")
    private String name;
    @SerializedName("fclName")
    private String fclName;
    @SerializedName("countryName")
    private String countryName;
    @SerializedName("fcodeName")
    private String fcodeName;
    @SerializedName("adminName1")
    private String adminName1;

    public String getAdminCode1() {
        return adminCode1;
    }

    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }

    public long getGeonameId() {
        return geonameId;
    }

    public String getToponymName() {
        return toponymName;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public String getFclName() {
        return fclName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getFcodeName() {
        return fcodeName;
    }

    public String getAdminName1() {
        return adminName1;
    }
}
