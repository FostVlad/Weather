package com.goloveschenko.weather.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goloveschenko.weather.R;
import com.goloveschenko.weather.entity.Forecastday;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private final Typeface typeface;

    private List<Forecastday> forecastdayList;

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

    public WeatherAdapter(List<Forecastday> forecastdayList, Typeface typeface) {
        this.forecastdayList = forecastdayList;
        this.typeface = typeface;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        Forecastday forecastday = forecastdayList.get(position);
        String dayOfWeek = WeatherUtils.getDayOfWeek(forecastday.getDate());
        holder.day.setText(dayOfWeek);
        Spanned iconCode = WeatherUtils.getWeatherIcon(forecastday.getDay().getCondition().getCode(), true);
        holder.icon.setText(iconCode);
        holder.icon.setTypeface(typeface);
        String temp = WeatherUtils.getTemperature(forecastday.getDay().getAvgTempC());
        holder.temperature.setText(temp);
    }

    @Override
    public int getItemCount() {
        return forecastdayList.size();
    }
}
