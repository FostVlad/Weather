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
import com.goloveschenko.weather.dao.OrmCity;
import com.goloveschenko.weather.dao.OrmWeather;
import com.goloveschenko.weather.utils.WeatherUtils;

import java.util.List;

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder> {
    private final Typeface typeface;
    private Context context;

    private List<OrmCity> cityList;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView location;
        TextView icon;
        TextView temp;

        ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.city_adapter_time);
            location = (TextView) itemView.findViewById(R.id.city_adapter_location);
            icon = (TextView) itemView.findViewById(R.id.city_adapter_icon);
            temp = (TextView) itemView.findViewById(R.id.city_adapter_temp);
        }
    }

    public CitiesRecyclerViewAdapter(List<OrmCity> cityList, Context context) {
        this.cityList = cityList;
        this.context = context;
        typeface = Typeface.createFromAsset(context.getAssets(), BuildConfig.WEATHER_FONT_PATH);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_adapter_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrmCity city = cityList.get(position);
        OrmWeather currentWeather = city.getWeatherList().get(0);
        holder.time.setText(currentWeather.getDate());
        holder.location.setText(city.getLocation());
        Spanned iconCode = WeatherUtils.getWeatherIcon(currentWeather.getIconCode(), currentWeather.getIsDay());
        holder.icon.setText(iconCode);
        holder.icon.setTypeface(typeface);
        String temp = String.valueOf(currentWeather.getTemp());
        holder.temp.setText(temp);
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}
