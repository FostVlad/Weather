package com.goloveschenko.weather.data.remote;

import com.goloveschenko.weather.data.model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("v1/current.json")
    Observable<WeatherResult> getWeather(@Query("q") String city);
    @GET("v1/forecast.json")
    Observable<WeatherResult> getWeatherByDays(@Query("q") String city, @Query("days") String days);
}
