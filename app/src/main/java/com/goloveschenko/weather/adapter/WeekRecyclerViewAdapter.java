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

public class WeekRecyclerViewAdapter extends RecyclerView.Adapter<WeekRecyclerViewAdapter.ViewHolder> {
    private final Typeface typeface;

    private List<OrmWeather> weekList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView icon;
        TextView tempMax;
        TextView tempMin;

        ViewHolder(View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.week_adapter_day);
            icon = (TextView) itemView.findViewById(R.id.week_adapter_icon);
            tempMax = (TextView) itemView.findViewById(R.id.week_adapter_temp_max);
            tempMin = (TextView) itemView.findViewById(R.id.week_adapter_temp_min);
        }
    }

    public WeekRecyclerViewAdapter(List<OrmWeather> weekList, Context context) {
        this.weekList = weekList;
        this.typeface = Typeface.createFromAsset(context.getAssets(), BuildConfig.WEATHER_FONT_PATH);
    }

    @Override
    public WeekRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_adapter_view, parent, false);
        return new WeekRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeekRecyclerViewAdapter.ViewHolder holder, int position) {
        OrmWeather forecastDay = weekList.get(position);
        String dayOfWeek = WeatherUtils.getDayOfWeek(forecastDay.getDate());
        holder.day.setText(dayOfWeek);
        Spanned iconCode = WeatherUtils.getWeatherIcon(forecastDay.getIconCode(), forecastDay.getIsDay());
        holder.icon.setText(iconCode);
        holder.icon.setTypeface(typeface);
        String tempMax = String.valueOf(forecastDay.getTempMax());
        holder.tempMax.setText(tempMax);
        String tempMin = String.valueOf(forecastDay.getTempMin());
        holder.tempMin.setText(tempMin);
    }

    @Override
    public int getItemCount() {
        return weekList.size();
    }
}
