package com.goloveschenko.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.adapter.CityRecyclerViewAdapter;
import com.goloveschenko.weather.dao.OrmCity;
import com.goloveschenko.weather.data.local.ILocalDataSource;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends BaseActivity {
    private RecyclerView recyclerViewCities;
    private List<OrmCity> cityList;

    @Override
    protected int getLayout() {
        return R.layout.activity_city;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerViewCities = (RecyclerView) findViewById(R.id.cities_view);
        recyclerViewCities.setLayoutManager(new LinearLayoutManager(this));

        cityList = new ArrayList<>();
        CityRecyclerViewAdapter adapter = new CityRecyclerViewAdapter(cityList, this);
        recyclerViewCities.setAdapter(adapter);

        makeView();
    }

    private void makeView() {
        ILocalDataSource localDataSource = ((WeatherApp) getApplication()).getLocalDataSource();
        cityList.addAll(localDataSource.getCityList());
        recyclerViewCities.getAdapter().notifyDataSetChanged();
    }
}
