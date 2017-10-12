package com.goloveschenko.weather.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.data.local.ILocalDataSource;
import com.goloveschenko.weather.fragment.CurrentForecastFragment;
import com.goloveschenko.weather.fragment.WeekForecastFragment;
import com.goloveschenko.weather.service.WeatherService;

public class WeatherActivity extends BaseActivity {
    public static final String BROADCAST_ACTION = "WeatherActivity_action";

    private FrameLayout container;
    private ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        setProgressBarVisible(true);
        container = (FrameLayout) findViewById(R.id.current_forecast_container);
        setContentVisible(false);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateWeatherView();
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);

        startService(new Intent(this, WeatherService.class));

        ImageView cityButton = (ImageView) findViewById(R.id.add_city_button);
        cityButton.setOnClickListener(view -> startActivityForResult(new Intent(WeatherActivity.this, CityActivity.class), 200));
    }



    private void updateWeatherView() {
        ILocalDataSource localDataSource = ((WeatherApp) getApplication()).getLocalDataSource();
        //===CURRENT FORECAST===
        CurrentForecastFragment currentForecastFragment = CurrentForecastFragment.getInstance(/*localDataSource.getCurrentForecast().getCityId()*/0L);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.current_forecast_container, currentForecastFragment)
                .commit();
        //===WEEK FORECAST===
        WeekForecastFragment weekForecastFragment = WeekForecastFragment.getInstance(/*localDataSource.getCurrentForecast().getCityId()*/0L);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.week_forecast_container, weekForecastFragment)
                .commit();

        setProgressBarVisible(false);
        setContentVisible(true);
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
