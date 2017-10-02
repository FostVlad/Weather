package com.goloveschenko.weather.dao;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.DaoException;

@Entity
public class OrmWeather {
    @Id(autoincrement = true)
    private Long id;
    private Long cityId;
    private String location;
    private String date;
    private int iconCode;
    private String details;
    private int humidity;
    private double pressure;
    private int temp;
    private int tempMin;
    private int tempMax;
    private boolean isDay;
    @Convert(converter = WeatherTypeConverter.class, columnType = Integer.class)
    private WeatherType type;

    @ToOne(joinProperty = "cityId")
    private OrmCity city;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1896156639)
    private transient OrmWeatherDao myDao;

    @Generated(hash = 218782970)
    public OrmWeather(Long id, Long cityId, String location, String date, int iconCode, String details,
            int humidity, double pressure, int temp, int tempMin, int tempMax, boolean isDay,
            WeatherType type) {
        this.id = id;
        this.cityId = cityId;
        this.location = location;
        this.date = date;
        this.iconCode = iconCode;
        this.details = details;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.isDay = isDay;
        this.type = type;
    }

    @Generated(hash = 109503595)
    public OrmWeather() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIconCode() {
        return this.iconCode;
    }

    public void setIconCode(int iconCode) {
        this.iconCode = iconCode;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getHumidity() {
        return this.humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return this.pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getTemp() {
        return this.temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getTempMin() {
        return this.tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return this.tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public boolean getIsDay() {
        return this.isDay;
    }

    public void setIsDay(boolean isDay) {
        this.isDay = isDay;
    }

    public WeatherType getType() {
        return this.type;
    }

    public void setType(WeatherType type) {
        this.type = type;
    }

    @Generated(hash = 1696970556)
    private transient Long city__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 830186693)
    public OrmCity getCity() {
        Long __key = this.cityId;
        if (city__resolvedKey == null || !city__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            OrmCityDao targetDao = daoSession.getOrmCityDao();
            OrmCity cityNew = targetDao.load(__key);
            synchronized (this) {
                city = cityNew;
                city__resolvedKey = __key;
            }
        }
        return city;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 22237661)
    public void setCity(OrmCity city) {
        synchronized (this) {
            this.city = city;
            cityId = city == null ? null : city.getId();
            city__resolvedKey = cityId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 464866394)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrmWeatherDao() : null;
    }

    public enum WeatherType {
        CURRENT(0), HOUR(1), WEEK(2);

        final int value;

        WeatherType(int value) {
            this.value = value;
        }
    }

    public static class WeatherTypeConverter implements PropertyConverter<WeatherType, Integer> {
        @Override
        public WeatherType convertToEntityProperty(Integer databaseValue) {
            for (WeatherType type : WeatherType.values()) {
                if (type.value == databaseValue) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public Integer convertToDatabaseValue(WeatherType entityProperty) {
            return entityProperty == null ? null : entityProperty.value;
        }
    }
}
