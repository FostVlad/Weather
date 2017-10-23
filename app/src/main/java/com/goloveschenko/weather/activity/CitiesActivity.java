package com.goloveschenko.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.adapter.CitiesRecyclerViewAdapter;
import com.goloveschenko.weather.dao.OrmCity;
import com.goloveschenko.weather.data.local.ILocalDataSource;

import java.util.ArrayList;
import java.util.List;

public class CitiesActivity extends BaseActivity {
    private RecyclerView recyclerViewCities;
    private List<OrmCity> cityList;

    @Override
    protected int getLayout() {
        return R.layout.activity_city;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cities_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerViewCities = (RecyclerView) findViewById(R.id.cities_view);
        recyclerViewCities.setLayoutManager(new LinearLayoutManager(this));

        cityList = new ArrayList<>();
        CitiesRecyclerViewAdapter adapter = new CitiesRecyclerViewAdapter(cityList, this);
        recyclerViewCities.setAdapter(adapter);

        makeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_city) {

        }
        return super.onOptionsItemSelected(item);
    }

    private void makeView() {
        ILocalDataSource localDataSource = ((WeatherApp) getApplication()).getLocalDataSource();
        cityList.addAll(localDataSource.getCityList());
        recyclerViewCities.getAdapter().notifyDataSetChanged();
    }
}
