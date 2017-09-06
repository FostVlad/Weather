package com.goloveschenko.weather.data.remote;

import com.goloveschenko.weather.data.model.ForecastWeather;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("v1/current.json")
    Observable<ForecastWeather> getWeather(@Query("q") String city);
    @GET("v1/forecast.json")
    Observable<ForecastWeather> getWeatherByDays(@Query("q") String city, @Query("days") String days);
}
