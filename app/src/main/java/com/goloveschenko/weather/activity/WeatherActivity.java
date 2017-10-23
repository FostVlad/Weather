package com.goloveschenko.weather.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.data.local.ILocalDataSource;
import com.goloveschenko.weather.fragment.CurrentForecastFragment;
import com.goloveschenko.weather.fragment.WeekForecastFragment;
import com.goloveschenko.weather.service.WeatherService;

public class WeatherActivity extends BaseActivity {
    public static final String BROADCAST_ACTION = "WeatherActivity_action";
    public static final String CITY_ID_EXTRA = "cityId_extra";

    private FrameLayout container;
    private ProgressBar progressBar;

    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        Intent serviceIntent = new Intent(this, WeatherService.class);
        serviceIntent.putExtra(CITY_ID_EXTRA, 0L);
        startService(serviceIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_show_city_list) {
            startActivityForResult(new Intent(WeatherActivity.this, CitiesActivity.class), 200);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeatherView() {
        ILocalDataSource localDataSource = ((WeatherApp) getApplication()).getLocalDataSource();
        //===CURRENT FORECAST===
        CurrentForecastFragment currentForecastFragment = CurrentForecastFragment.getInstance(localDataSource.getCityList().get(0).getId());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.current_forecast_container, currentForecastFragment)
                .commit();
        //===WEEK FORECAST===
        WeekForecastFragment weekForecastFragment = WeekForecastFragment.getInstance(localDataSource.getCityList().get(0).getId());
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
