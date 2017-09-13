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

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.BuildConfig;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.adapter.DayRecyclerViewAdapter;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.data.local.ILocalDataSource;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

public class CurrentForecastFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    private TextView location;
    private TextView updateTime;
    private TextView weatherIcon;
    private TextView details;
    private TextView humidity;
    private TextView pressure;
    private TextView temperature;

    private RecyclerView recyclerViewDay;
    private List<OrmWeather> forecastDay;

    public CurrentForecastFragment() {
    }

    public static CurrentForecastFragment getInstance(String cityId) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, cityId);
        CurrentForecastFragment currentForecastFragment = new CurrentForecastFragment();
        currentForecastFragment.setArguments(args);
        return currentForecastFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_forecast, container, false);

        Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), BuildConfig.WEATHER_FONT_PATH);
        location = (TextView) view.findViewById(R.id.location);
        updateTime = (TextView) view.findViewById(R.id.update_time);
        weatherIcon = (TextView) view.findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        details = (TextView) view.findViewById(R.id.details);
        humidity = (TextView) view.findViewById(R.id.humidity);
        pressure = (TextView) view.findViewById(R.id.pressure);
        temperature = (TextView) view.findViewById(R.id.temperature);

        recyclerViewDay = (RecyclerView) view.findViewById(R.id.forecast_day);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDay.setLayoutManager(layoutManager);

        forecastDay = new ArrayList<>();
        DayRecyclerViewAdapter adapter = new DayRecyclerViewAdapter(forecastDay, weatherFont);
        recyclerViewDay.setAdapter(adapter);

        makeView();

        return view;
    }

   private void makeView() {
        ILocalDataSource localDataSource = ((WeatherApp) getActivity().getApplication()).getLocalDataSource();

        OrmWeather current = localDataSource.getCurrentForecast();
        location.setText(current.getLocation());
        updateTime.setText(current.getDate());
        Spanned iconCode = WeatherUtils.getWeatherIcon(current.getIconCode(), current.getIsDay());
        weatherIcon.setText(iconCode);
        details.setText(current.getDetails());
        String humidityText = "Humidity: " + current.getHumidity() + "%";
        humidity.setText(humidityText);
        String pressureText = "Pressure: " + current.getPressure() + " hPa";
        pressure.setText(pressureText);
        String temp = current.getTemp() + "°";
        temperature.setText(temp);

        //===DAY===
        List<OrmWeather> hourList = localDataSource.getForecastByType(OrmWeather.WeatherType.HOUR);
        forecastDay.addAll(hourList);
        recyclerViewDay.getAdapter().notifyDataSetChanged();
    }
}
