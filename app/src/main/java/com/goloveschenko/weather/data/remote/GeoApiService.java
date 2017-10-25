package com.goloveschenko.weather.data.remote;

import com.goloveschenko.weather.data.model.GeoCities;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoApiService {
    @GET("searchJSON")
    Observable<GeoCities> getCities(
            @Query("name_startsWith") String name,
            @Query("maxRows") int maxRows,
            @Query("lang") String lang,
            @Query("cities") String cities,
            @Query("username") String userName);
}
