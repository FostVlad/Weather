package com.goloveschenko.weather.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goloveschenko.weather.BuildConfig;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.adapter.WeatherAdapter;
import com.goloveschenko.weather.entity.Forecastday;
import com.goloveschenko.weather.entity.WeatherResult;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherDetailFragment extends Fragment {
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
    private WeatherResult weather;

    public WeatherDetailFragment() {
    }

    public void setWeather(WeatherResult weather) {
        this.weather = weather;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_detail, container, false);

        Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), BuildConfig.WEATHER_FONT_PATH);
        location = (TextView) view.findViewById(R.id.location);
        updateTime = (TextView) view.findViewById(R.id.update_time);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        details = (TextView) view.findViewById(R.id.details);
        humidity = (TextView) view.findViewById(R.id.humidity);
        pressure = (TextView) view.findViewById(R.id.pressure);
        temperature = (TextView) view.findViewById(R.id.temperature);

        recyclerViewForecast = (RecyclerView) view.findViewById(R.id.forecastday);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewForecast.setLayoutManager(layoutManager);

        forecastdayList = new ArrayList<>();
        WeatherAdapter adapter = new WeatherAdapter(forecastdayList, weatherFont);
        recyclerViewForecast.setAdapter(adapter);

        makeView();

        return view;
    }

   private void makeView() {
        //===LOCATION===
        String locationText = weather.getLocation().getName().toUpperCase(Locale.US) + ", " + weather.getLocation().getCountry().toUpperCase(Locale.US);
        location.setText(locationText);
        //===DATE===
        String date = WeatherUtils.convertDate(weather.getLocation().getLocaltime());
        updateTime.setText(date);
        //===WEATHER ICON===
        Spanned iconCode = WeatherUtils.getWeatherIcon(weather.getCurrent().getCondition().getCode(), weather.getCurrent().getIsDay() == DAY_PARAMETER);
        weatherIcon.setText(iconCode);
        //===DETAILS===
        details.setText(weather.getCurrent().getCondition().getText());
        //===HUMIDITY===
        String humidityText = "Humidity: " + weather.getCurrent().getHumidity() + "%";
        humidity.setText(humidityText);
        //===PRESSURE===
        String pressureText = "Pressure: " + weather.getCurrent().getPressure() + " hPa";
        pressure.setText(pressureText);
        //===TEMPERATURE===
        temperature.setText(WeatherUtils.getTemperature(weather.getCurrent().getTemp()));

        //===FORECAST===
        forecastdayList.addAll(weather.getForecast().getForecastday());
        recyclerViewForecast.getAdapter().notifyDataSetChanged();
    }
}
