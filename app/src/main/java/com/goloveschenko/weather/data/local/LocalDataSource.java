package com.goloveschenko.weather.data.local;

import android.content.Context;

import com.goloveschenko.weather.dao.DaoMaster;
import com.goloveschenko.weather.dao.DaoSession;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.dao.OrmWeatherDao;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class LocalDataSource implements ILocalDataSource {
    private static final String DB_NAME = "weather.db";
    private static LocalDataSource instance;
    private DaoSession daoSession;

    private LocalDataSource(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static LocalDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDataSource(context);
        }
        return instance;
    }

    @Override
    public List<OrmWeather> getForecast() {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        return weatherDao.loadAll();
    }

    @Override
    public void refreshForecast(List<OrmWeather> forecast) {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        weatherDao.deleteAll();
        weatherDao.insertInTx(forecast);
    }
}
