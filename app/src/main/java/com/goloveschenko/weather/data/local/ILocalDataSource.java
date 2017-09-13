package com.goloveschenko.weather.data.local;

import com.goloveschenko.weather.dao.OrmWeather;

import java.util.List;

public interface ILocalDataSource {
    OrmWeather getCurrentForecast();
    List<OrmWeather> getForecastByType(OrmWeather.WeatherType type);
    void refreshForecast(List<OrmWeather> forecast);
}
