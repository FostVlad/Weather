package com.goloveschenko.weather.data.local;

import com.goloveschenko.weather.dao.OrmWeather;

import java.util.List;

public interface ILocalDataSource {
    List<OrmWeather> getForecast();
    void refreshForecast(List<OrmWeather> forecast);
}
