package com.goloveschenko.weather.data.local;

import android.content.Context;

import com.goloveschenko.weather.dao.DaoMaster;
import com.goloveschenko.weather.dao.DaoSession;
import com.goloveschenko.weather.dao.OrmCity;
import com.goloveschenko.weather.dao.OrmCityDao;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.dao.OrmWeather.WeatherType;
import com.goloveschenko.weather.dao.OrmWeather.WeatherTypeConverter;
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
    public OrmWeather getCurrentForecast(long cityId) {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        WeatherTypeConverter converter = new WeatherTypeConverter();
        List<OrmWeather> forecast = weatherDao.queryBuilder()
                .where(OrmWeatherDao.Properties.CityId.eq(cityId),
                       OrmWeatherDao.Properties.Type.eq(converter.convertToDatabaseValue(WeatherType.CURRENT)))
                .limit(1)
                .build()
                .list();
        return forecast.size() != 0 ? forecast.get(0) : null;
    }

    @Override
    public List<OrmWeather> getForecastByType(long cityId, OrmWeather.WeatherType type) {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        WeatherTypeConverter converter = new WeatherTypeConverter();
        return weatherDao.queryBuilder()
                .where(OrmWeatherDao.Properties.CityId.eq(cityId),
                       OrmWeatherDao.Properties.Type.eq(converter.convertToDatabaseValue(type)))
                .orderAsc(OrmWeatherDao.Properties.Date)
                .build()
                .list();
    }

    @Override
    public void refreshForecast(long cityId, List<OrmWeather> forecast) {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        weatherDao.queryBuilder()
                .where(OrmWeatherDao.Properties.CityId.eq(cityId))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        weatherDao.insertInTx(forecast);
    }

    @Override
    public void refreshAllForecast(List<OrmWeather> forecast) {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        weatherDao.deleteAll();
        weatherDao.insertInTx(forecast);
    }

    @Override
    public void deleteForecast(long cityId) {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        weatherDao.queryBuilder()
                .where(OrmWeatherDao.Properties.CityId.eq(cityId))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    @Override
    public void deleteAllForecast() {
        OrmWeatherDao weatherDao = daoSession.getOrmWeatherDao();
        weatherDao.deleteAll();
    }

    @Override
    public List<OrmCity> getCityList() {
        OrmCityDao cityDao = daoSession.getOrmCityDao();
        List<OrmCity> cityList = cityDao.loadAll();
        if (cityList.size() == 0) {
            OrmCity ormCity = new OrmCity(0L, "Minsk");
            ormCity.__setDaoSession(daoSession);
            cityList.add(ormCity);
        }
        return cityList;
    }

    @Override
    public void addCity(OrmCity city) {
        OrmCityDao cityDao = daoSession.getOrmCityDao();
        cityDao.insertOrReplace(city);
    }

    @Override
    public void removeCity(OrmCity city) {
        OrmCityDao cityDao = daoSession.getOrmCityDao();
        cityDao.delete(city);
    }
}
