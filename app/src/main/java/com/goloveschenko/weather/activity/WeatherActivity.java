package com.goloveschenko.weather.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.widget.TextView;
import android.widget.Toast;

import com.goloveschenko.weather.BuildConfig;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.adapter.WeatherAdapter;
import com.goloveschenko.weather.data.WeatherClient;
import com.goloveschenko.weather.data.WeatherService;
import com.goloveschenko.weather.entity.Forecastday;
import com.goloveschenko.weather.entity.WeatherResult;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherActivity extends BaseActivity {

    private static final String CITY_NAME = "Minsk";
    private static final String DAYS_COUNT = "7";
    private static final int DAY_PARAMETER = 1;

    private TextView location;
    private TextView updateTime;
    private TextView weatherIcon;
    private TextView details;
    private TextView humidity;
    private TextView pressure;
    private TextView temperature;

    private RecyclerView recyclerViewForecast;

    private List<Forecastday> forecastdayList;

    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Typeface weatherFont = Typeface.createFromAsset(getAssets(), BuildConfig.WEATHER_FONT_PATH);

        location = (TextView) findViewById(R.id.location);
        updateTime = (TextView) findViewById(R.id.update_time);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        details = (TextView) findViewById(R.id.details);
        humidity = (TextView) findViewById(R.id.humidity);
        pressure = (TextView) findViewById(R.id.pressure);
        temperature = (TextView) findViewById(R.id.temperature);

        recyclerViewForecast = (RecyclerView) findViewById(R.id.forecastday);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewForecast.setLayoutManager(layoutManager);

        forecastdayList = new ArrayList<>();
        WeatherAdapter adapter = new WeatherAdapter(forecastdayList, weatherFont);
        recyclerViewForecast.setAdapter(adapter);

        WeatherService weatherService = WeatherClient.getClient().create(WeatherService.class);
        weatherService.getWeatherByDays(CITY_NAME, DAYS_COUNT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateWeatherView,
                           e -> failureMessage(e.getMessage())
                );
    }

    private void updateWeatherView(WeatherResult weatherResult) {
        //===LOCATION===
        String locationText = weatherResult.getLocation().getName().toUpperCase(Locale.US) + ", " + weatherResult.getLocation().getCountry().toUpperCase(Locale.US);
        location.setText(locationText);
        //===DATE===
        String date = WeatherUtils.convertDate(weatherResult.getLocation().getLocaltime());
        updateTime.setText(date);
        //===WEATHER ICON===
        Spanned iconCode = WeatherUtils.getWeatherIcon(weatherResult.getCurrent().getCondition().getCode(), weatherResult.getCurrent().getIsDay() == DAY_PARAMETER);
        weatherIcon.setText(iconCode);
        //===DETAILS===
        details.setText(weatherResult.getCurrent().getCondition().getText());
        //===HUMIDITY===
        String humidityText = "Humidity: " + weatherResult.getCurrent().getHumidity() + "%";
        humidity.setText(humidityText);
        //===PRESSURE===
        String pressureText = "Pressure: " + weatherResult.getCurrent().getPressure() + " hPa";
        pressure.setText(pressureText);
        //===TEMPERATURE===
        temperature.setText(WeatherUtils.getTemperature(weatherResult.getCurrent().getTemp()));

        //===FORECAST===
        forecastdayList.addAll(weatherResult.getForecast().getForecastday());
        recyclerViewForecast.getAdapter().notifyDataSetChanged();
    }

    private void failureMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
