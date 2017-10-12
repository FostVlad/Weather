package com.goloveschenko.weather.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.goloveschenko.WeatherApp;
import com.goloveschenko.weather.activity.WeatherActivity;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.data.local.ILocalDataSource;
import com.goloveschenko.weather.data.model.ForecastDay;
import com.goloveschenko.weather.data.model.ForecastWeather;
import com.goloveschenko.weather.data.model.Hour;
import com.goloveschenko.weather.data.remote.WeatherApiClient;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherService extends IntentService {
    private static final String SERVICE_NAME = "WeatherService";

    private static final String CITY_NAME = "Minsk";
    private static final String DAYS_COUNT = "7";
    private static final int DAY_PARAMETER = 1;

    private ILocalDataSource localDataSource;

    public WeatherService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            localDataSource = ((WeatherApp) getApplication()).getLocalDataSource();
            WeatherApiClient.getClient().getWeatherByDays(CITY_NAME, DAYS_COUNT)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::updateLocalWeather,
                            e -> failureMessage(e.getMessage()),
                            //sending the flag of the completed task
                            () -> sendBroadcast(new Intent(WeatherActivity.BROADCAST_ACTION))
                    );
        }
    }

    private void updateLocalWeather(ForecastWeather forecastWeather) {
        OrmWeather ormWeather = getCurrentWeather(forecastWeather);
        List<OrmWeather> weatherList = getDayForecast(forecastWeather);
        weatherList.add(0, ormWeather);
        weatherList.addAll(getWeekForecast(forecastWeather));

        localDataSource.refreshForecast(0L, weatherList);
    }

    private OrmWeather getCurrentWeather(ForecastWeather forecastWeather) {
        OrmWeather ormWeather = new OrmWeather();
        ormWeather.setCityId(/*forecastWeather.getLocation().getTzId()*/0L);
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

    private List<OrmWeather> getDayForecast(ForecastWeather forecastWeather) {
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
                        ormWeather.setCityId(/*forecastWeather.getLocation().getTzId()*/0L);
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

    private List<OrmWeather> getWeekForecast(ForecastWeather forecastWeather) {
        List<OrmWeather> weatherList = new ArrayList<>();
        OrmWeather ormWeather;
        for (ForecastDay day : forecastWeather.getForecast().getForecastDay()) {
            ormWeather = new OrmWeather();
            ormWeather.setCityId(/*forecastWeather.getLocation().getTzId()*/0L);
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
}
