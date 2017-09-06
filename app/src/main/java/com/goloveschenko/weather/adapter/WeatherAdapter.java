package com.goloveschenko.weather.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goloveschenko.weather.R;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.data.model.ForecastDay;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private final Typeface typeface;

    private List<OrmWeather> forecastDayList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView icon;
        TextView temperature;

        ViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.weather_item_day);
            icon = (TextView) itemView.findViewById(R.id.weather_item_icon);
            temperature = (TextView) itemView.findViewById(R.id.weather_item_temperature);
        }
    }

    public WeatherAdapter(List<OrmWeather> forecastDayList, Typeface typeface) {
        this.forecastDayList = forecastDayList;
        this.typeface = typeface;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        OrmWeather forecastDay = forecastDayList.get(position);
        String dayOfWeek = WeatherUtils.getDayOfWeek(forecastDay.getDate());
        holder.day.setText(dayOfWeek);
        Spanned iconCode = WeatherUtils.getWeatherIcon(forecastDay.getIconCode(), forecastDay.getIsDay());
        holder.icon.setText(iconCode);
        holder.icon.setTypeface(typeface);
        String temp = forecastDay.getTemp() + "°";
        holder.temperature.setText(temp);
    }

    @Override
    public int getItemCount() {
        return forecastDayList.size();
    }
}
