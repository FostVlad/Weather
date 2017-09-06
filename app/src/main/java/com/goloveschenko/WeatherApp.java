package com.goloveschenko;

import android.app.Application;

import com.goloveschenko.weather.data.local.ILocalDataSource;
import com.goloveschenko.weather.data.local.LocalDataSource;

public class WeatherApp extends Application {
    private ILocalDataSource localDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        localDataSource = LocalDataSource.getInstance(this);
    }

    public ILocalDataSource getLocalDataSource() {
        return localDataSource;
    }
}
