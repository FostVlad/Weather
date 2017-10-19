package com.goloveschenko.weather.data.local;

import com.goloveschenko.weather.dao.OrmCity;
import com.goloveschenko.weather.dao.OrmWeather;

import java.util.List;

public interface ILocalDataSource {
    OrmWeather getCurrentForecast(long cityId);
    List<OrmWeather> getForecastByType(long cityId, OrmWeather.WeatherType type);
    void refreshForecast(long cityId, List<OrmWeather> forecast);
    void refreshAllForecast(List<OrmWeather> forecast);
    void deleteForecast(long cityId);
    void deleteAllForecast();

    List<OrmCity> getCityList();
    OrmCity getCityById(Long id);
    void addCity(OrmCity city);
    void removeCity(OrmCity city);
}
