package com.goloveschenko.weather.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrmWeather {
    @Id(autoincrement = true)
    private Long id;
    private String cityId;
    private String location;
    private String date;
    private int iconCode;
    private String details;
    private int humidity;
    private double pressure;
    private int temp;
    private boolean isDay;
    @Generated(hash = 487943109)
    public OrmWeather(Long id, String cityId, String location, String date,
            int iconCode, String details, int humidity, double pressure, int temp,
            boolean isDay) {
        this.id = id;
        this.cityId = cityId;
        this.location = location;
        this.date = date;
        this.iconCode = iconCode;
        this.details = details;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temp = temp;
        this.isDay = isDay;
    }
    @Generated(hash = 109503595)
    public OrmWeather() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCityId() {
        return this.cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getIconCode() {
        return this.iconCode;
    }
    public void setIconCode(int iconCode) {
        this.iconCode = iconCode;
    }
    public String getDetails() {
        return this.details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
    public int getHumidity() {
        return this.humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public double getPressure() {
        return this.pressure;
    }
    public void setPressure(double pressure) {
        this.pressure = pressure;
    }
    public int getTemp() {
        return this.temp;
    }
    public void setTemp(int temp) {
        this.temp = temp;
    }
    public boolean getIsDay() {
        return this.isDay;
    }
    public void setIsDay(boolean isDay) {
        this.isDay = isDay;
    }
}
