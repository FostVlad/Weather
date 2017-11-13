package com.goloveschenko.weather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.goloveschenko.weather.R;
import com.goloveschenko.weather.data.model.GeoCity;
import com.goloveschenko.weather.data.remote.GeoApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchCityActivity extends BaseActivity {
    private static final String CITY_KEY = "city";
    private static final String COUNTRY_KEY = "country";

    private ListView listViewCities;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_city;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Map<String, String>> data = new ArrayList<>();
        SearchView searchCities = (SearchView) findViewById(R.id.search_city_input);
        searchCities.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                data.clear();
                GeoApiClient.getClient().getCities(query, 10, Locale.getDefault().toString(), "cities15000")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(geoCities -> {
                            List<GeoCity> cityList = geoCities.getCities();
                            for (GeoCity city : cityList) {
                                Map<String, String> datum = new HashMap<>(2);
                                datum.put(CITY_KEY, city.getAdminName1());
                                datum.put(COUNTRY_KEY, city.getCountryName());
                                data.add(datum);
                            }
                            ((SimpleAdapter) listViewCities.getAdapter()).notifyDataSetChanged();
                        });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listViewCities = (ListView) findViewById(R.id.search_city_result);

        SimpleAdapter listAdapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {CITY_KEY, COUNTRY_KEY},
                new int[] {android.R.id.text1,
                        android.R.id.text2});

        listViewCities.setAdapter(listAdapter);

        listViewCities.setOnItemClickListener((adapterView, view, i, l) -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}
