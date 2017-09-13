package com.goloveschenko.weather.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.data.local.ILocalDataSource;
import com.goloveschenko.weather.data.model.ForecastDay;
import com.goloveschenko.weather.data.model.Hour;
import com.goloveschenko.weather.data.remote.WeatherClient;
import com.goloveschenko.weather.data.remote.WeatherService;
import com.goloveschenko.weather.data.model.ForecastWeather;
import com.goloveschenko.weather.fragment.CurrentForecastFragment;
import com.goloveschenko.weather.fragment.WeekForecastFragment;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherActivity extends BaseActivity {
    private static final String CITY_NAME = "Minsk";
    private static final String DAYS_COUNT = "7";
    private static final int DAY_PARAMETER = 1;

    private FrameLayout container;
    private ProgressBar progressBar;

    private Disposable disposable;

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

        WeatherService weatherService = WeatherClient.getClient().create(WeatherService.class);
        disposable = weatherService.getWeatherByDays(CITY_NAME, DAYS_COUNT)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::updateWeatherView,
                        e -> failureMessage(e.getMessage())
                );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void updateWeatherView(ForecastWeather forecastWeather) {
        ILocalDataSource localDataSource = ((WeatherApp) getApplication()).getLocalDataSource();
        OrmWeather ormWeather = getCurrentWeather(forecastWeather);
        List<OrmWeather> weatherList = getCurrentHourForecast(forecastWeather);
        weatherList.add(0, ormWeather);
        weatherList.addAll(getDaysForecast(forecastWeather));

        localDataSource.refreshForecast(weatherList);

        //===CURRENT FORECAST===
        CurrentForecastFragment currentForecastFragment = CurrentForecastFragment.getInstance(forecastWeather.getLocation().getTzId());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.current_forecast_container, currentForecastFragment)
                .commit();
        //===WEEK FORECAST===
        WeekForecastFragment weekForecastFragment = WeekForecastFragment.getInstance(forecastWeather.getLocation().getTzId());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.week_forecast_container, weekForecastFragment)
                .commit();

        setProgressBarVisible(false);
        setContentVisible(true);
    }

    private OrmWeather getCurrentWeather(ForecastWeather forecastWeather) {
        OrmWeather ormWeather = new OrmWeather();
        ormWeather.setCityId(forecastWeather.getLocation().getTzId());
        ormWeather.setLocation(forecastWeather.getLocation().getName().toUpperCase(Locale.US) + ", " + forecastWeather.getLocation().getCountry().toUpperCase(Locale.US));
        ormWeather.setDate(WeatherUtils.getConvertTime(forecastWeather.getLocation().getLocaltime()));
        ormWeather.setIconCode(forecastWeather.getCurrent().getCondition().getCode());
        ormWeather.setIsDay(forecastWeather.getCurrent().getIsDay() == DAY_PARAMETER);
        ormWeather.setDetails(forecastWeather.getCurrent().getCondition().getText());
        ormWeather.setHumidity(forecastWeather.getCurrent().getHumidity());
        ormWeather.setPressure(forecastWeather.getCurrent().getPressure());
        ormWeather.setTemp((int) forecastWeather.getCurrent().getTemp());
        ormWeather.setTempMin((int) forecastWeather.getForecast().getForecastDay().get(0).getDay().getMinTempC());
        ormWeather.setTempMax((int) forecastWeather.getForecast().getForecastDay().get(0).getDay().getMaxTempC());
        ormWeather.setType(OrmWeather.WeatherType.CURRENT);
        return ormWeather;
    }

    private List<OrmWeather> getCurrentHourForecast(ForecastWeather forecastWeather) {
        List<OrmWeather> weatherList = new ArrayList<>();
        OrmWeather ormWeather;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            Calendar nextDay = Calendar.getInstance();
            nextDay.add(Calendar.DATE, 1);
            for (ForecastDay forecastDay : forecastWeather.getForecast().getForecastDay()) {
                for (Hour hour : forecastDay.getHour()) {
                    if (df.parse(hour.getTime()).after(Calendar.getInstance().getTime()) && df.parse(hour.getTime()).before(nextDay.getTime())) {
                        ormWeather = new OrmWeather();
                        ormWeather.setCityId(forecastWeather.getLocation().getTzId());
                        ormWeather.setLocation("");
                        ormWeather.setDate(hour.getTime());
                        ormWeather.setIconCode(hour.getCondition().getCode());
                        ormWeather.setIsDay(hour.getIsDay() == DAY_PARAMETER);
                        ormWeather.setDetails("");
                        ormWeather.setHumidity(hour.getHumidity());
                        ormWeather.setPressure(hour.getPressureMb());
                        ormWeather.setTemp((int) hour.getTempC());
                        ormWeather.setTempMin(0);
                        ormWeather.setTempMax(0);
                        ormWeather.setType(OrmWeather.WeatherType.HOUR);

                        weatherList.add(ormWeather);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    private List<OrmWeather> getDaysForecast(ForecastWeather forecastWeather) {
        List<OrmWeather> weatherList = new ArrayList<>();
        OrmWeather ormWeather;
        for (ForecastDay day : forecastWeather.getForecast().getForecastDay()) {
            ormWeather = new OrmWeather();
            ormWeather.setCityId(forecastWeather.getLocation().getTzId());
            ormWeather.setLocation("");
            ormWeather.setDate(day.getDate());
            ormWeather.setIconCode(day.getDay().getCondition().getCode());
            ormWeather.setIsDay(forecastWeather.getCurrent().getIsDay() == DAY_PARAMETER);
            ormWeather.setDetails("");
            ormWeather.setHumidity(0);
            ormWeather.setPressure(0);
            ormWeather.setTemp((int) day.getDay().getAvgTempC());
            ormWeather.setTempMin((int) day.getDay().getMinTempC());
            ormWeather.setTempMax((int) day.getDay().getMaxTempC());
            ormWeather.setType(OrmWeather.WeatherType.WEEK);

            weatherList.add(ormWeather);
        }
        return weatherList;
    }

    private void failureMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
