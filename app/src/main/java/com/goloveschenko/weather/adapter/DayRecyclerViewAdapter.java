package com.goloveschenko.weather.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goloveschenko.weather.BuildConfig;
import com.goloveschenko.weather.R;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.List;

public class DayRecyclerViewAdapter extends RecyclerView.Adapter<DayRecyclerViewAdapter.ViewHolder> {
    private final Typeface typeface;
    private Context context;

    private List<OrmWeather> dayList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView hour;
        TextView icon;
        TextView temp;

        ViewHolder(View itemView) {
            super(itemView);
            hour = (TextView) itemView.findViewById(R.id.day_adapter_hour);
            icon = (TextView) itemView.findViewById(R.id.day_adapter_icon);
            temp = (TextView) itemView.findViewById(R.id.day_adapter_temp);
        }
    }

    public DayRecyclerViewAdapter(List<OrmWeather> dayList, Context context) {
        this.dayList = dayList;
        this.context = context;
        this.typeface = Typeface.createFromAsset(context.getAssets(), BuildConfig.WEATHER_FONT_PATH);
    }

    @Override
    public DayRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_adapter_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayRecyclerViewAdapter.ViewHolder holder, int position) {
        OrmWeather forecastDay = dayList.get(position);
        String dayOfWeek = WeatherUtils.getHour(forecastDay.getDate());
        holder.hour.setText(dayOfWeek);
        Spanned iconCode = WeatherUtils.getWeatherIcon(forecastDay.getIconCode(), forecastDay.getIsDay());
        holder.icon.setText(iconCode);
        holder.icon.setTypeface(typeface);
        String temp = forecastDay.getTemp() + context.getResources().getString(R.string.temp_title);
        holder.temp.setText(temp);
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }
}
