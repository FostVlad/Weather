package com.goloveschenko.weather.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class OrmWeather {
    @Id
    private String id;
    private String location;
    private String date;
    private int iconCode;
    private String details;
    private int humidity;
    private double pressure;
    private int temp;
    private boolean isDay;
    @Generated(hash = 6490740)
    public OrmWeather(String id, String location, String date, int iconCode,
            String details, int humidity, double pressure, int temp,
            boolean isDay) {
        this.id = id;
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
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
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
