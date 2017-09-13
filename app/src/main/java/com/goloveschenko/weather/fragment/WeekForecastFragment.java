package com.goloveschenko.weather.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.BuildConfig;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.adapter.WeekRecyclerViewAdapter;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.data.local.ILocalDataSource;

import java.util.ArrayList;
import java.util.List;

public class WeekForecastFragment extends Fragment {
    public static final String ARG_ITEM_ID = "item_id";

    private RecyclerView recyclerViewWeek;
    private List<OrmWeather> forecastWeek;

    public WeekForecastFragment() {
    }

    public static WeekForecastFragment getInstance(String cityId) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, cityId);
        WeekForecastFragment weekForecastFragment = new WeekForecastFragment();
        weekForecastFragment.setArguments(args);
        return weekForecastFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_forecast, container, false);

        recyclerViewWeek = (RecyclerView) view.findViewById(R.id.forecast_week);
        recyclerViewWeek.setLayoutManager(new LinearLayoutManager(view.getContext()));

        forecastWeek = new ArrayList<>();
        Typeface weatherFont = Typeface.createFromAsset(getActivity().getAssets(), BuildConfig.WEATHER_FONT_PATH);
        WeekRecyclerViewAdapter adapter = new WeekRecyclerViewAdapter(forecastWeek, weatherFont);
        recyclerViewWeek.setAdapter(adapter);

        makeView();

        return view;
    }

    private void makeView() {
        ILocalDataSource localDataSource = ((WeatherApp) getActivity().getApplication()).getLocalDataSource();
        List<OrmWeather> dayList = localDataSource.getForecastByType(OrmWeather.WeatherType.WEEK);
        forecastWeek.addAll(dayList);
        recyclerViewWeek.getAdapter().notifyDataSetChanged();
    }
}
