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

public class WeatherDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    private TextView location;
    private TextView updateTime;
    private TextView weatherIcon;
    private TextView details;
    private TextView humidity;
    private TextView pressure;
    private TextView temperature;
    private RecyclerView recyclerViewForecast;

    private List<OrmWeather> forecastDayList;

    public WeatherDetailFragment() {
    }

    public static WeatherDetailFragment getInstance(String cityId) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, cityId);
        WeatherDetailFragment weatherDetailFragment = new WeatherDetailFragment();
        weatherDetailFragment.setArguments(args);
        return weatherDetailFragment;
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

        forecastDayList = new ArrayList<>();
        DayRecyclerViewAdapter adapter = new DayRecyclerViewAdapter(forecastDayList, weatherFont);
        recyclerViewForecast.setAdapter(adapter);

        makeView();

        return view;
    }

   private void makeView() {
        ILocalDataSource localDataSource = ((WeatherApp) getActivity().getApplication()).getLocalDataSource();
        List<OrmWeather> weatherList = localDataSource.getForecast();

        location.setText(weatherList.get(0).getLocation());
        updateTime.setText(weatherList.get(0).getDate());
        Spanned iconCode = WeatherUtils.getWeatherIcon(weatherList.get(0).getIconCode(), weatherList.get(0).getIsDay());
        weatherIcon.setText(iconCode);
        details.setText(weatherList.get(0).getDetails());
        String humidityText = "Humidity: " + weatherList.get(0).getHumidity() + "%";
        humidity.setText(humidityText);
        String pressureText = "Pressure: " + weatherList.get(0).getPressure() + " hPa";
        pressure.setText(pressureText);
        String temp = weatherList.get(0).getTemp() + "°";
        temperature.setText(temp);

        //===WEEK===
        forecastDayList.addAll(weatherList.subList(1, weatherList.size()));
        recyclerViewForecast.getAdapter().notifyDataSetChanged();
    }
}
