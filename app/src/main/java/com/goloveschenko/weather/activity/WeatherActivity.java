package com.goloveschenko.weather.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goloveschenko.weather.R;
import com.goloveschenko.weather.data.WeatherClient;
import com.goloveschenko.weather.data.WeatherService;
import com.goloveschenko.weather.entity.WeatherResult;
import com.goloveschenko.weather.fragment.WeatherDetailFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherActivity extends BaseActivity {
    private static final String CITY_NAME = "Minsk";
    private static final String DAYS_COUNT = "7";

    private FrameLayout container;
    private ProgressBar progressBar;

    private Disposable disposable;

    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        setProgressBarVisible(true);
        container = (FrameLayout) findViewById(R.id.weather_detail_container);
        setContentVisible(false);

        WeatherService weatherService = WeatherClient.getClient().create(WeatherService.class);
        disposable = weatherService.getWeatherByDays(CITY_NAME, DAYS_COUNT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateWeatherView,
                        e -> failureMessage(e.getMessage())
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void updateWeatherView(WeatherResult weather) {
        WeatherDetailFragment fragment = new WeatherDetailFragment();
        fragment.setWeather(weather);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.weather_detail_container, fragment)
                .commit();
        setProgressBarVisible(false);
        setContentVisible(true);
    }

    private void failureMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void setProgressBarVisible(boolean visible) {
        if (visible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setContentVisible(boolean visible) {
        if (visible) {
            container.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.GONE);
        }
    }
}
