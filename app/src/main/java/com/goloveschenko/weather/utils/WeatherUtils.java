package com.goloveschenko.weather.utils;

import android.text.Html;
import android.text.Spanned;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherUtils {

    public static Spanned getWeatherIcon(int iconCode, boolean isDay) {
        String result;
        switch (iconCode) {
            case 1000:
                result = isDay ? "&#xf00d;" : "&#xf02e;";
                break;
            case 1003:
                result = isDay ? "&#xf002;" : "&#xf086;";
                break;
            case 1006:
                result = "&#xf041;";
                break;
            case 1009:
                result = "&#xf013;";
                break;
            case 1030:
                result = "&#xf021;";
                break;
            case 1063:
            case 1180:
            case 1186:
            case 1192:
                result = isDay ? "&#xf008;" : "&#xf028;";
                break;
            case 1066:
            case 1210:
            case 1216:
            case 1222:
            case 1255:
            case 1258:
            case 1261:
            case 1264:
                result = isDay ? "&#xf00a;" : "&#xf02a;";
                break;
            case 1069:
            case 1249:
            case 1252:
                result = isDay ? "&#xf0b2;" : "&#xf0b4;";
                break;
            case 1072:
            case 1150:
            case 1153:
            case 1168:
            case 1171:
            case 1204:
            case 1207:
                result = "&#xf0b5;";
                break;
            case 1087:
                result = isDay ? "&#xf005;" : "&#xf025;";
                break;
            case 1114:
            case 1117:
                result = "&#xf064;";
                break;
            case 1135:
            case 1147:
                result = "&#xf014;";
                break;
            case 1183:
            case 1189:
            case 1195:
                result = "&#xf019;";
                break;
            case 1198:
            case 1201:
                result = "&#xf017;";
                break;
            case 1213:
            case 1219:
            case 1225:
            case 1237:
                result = "&#xf01b;";
                break;
            case 1240:
            case 1243:
            case 1246:
                result = isDay ? "&#xf009;" : "&#xf029;";
                break;
            case 1273:
            case 1276:
                result = isDay ? "&#xf010;" : "&#xf02d;";
                break;
            case 1279:
            case 1282:
                result = isDay ? "&#xf06b;" : "&#xf06d;";
                break;
            default:
                result = "&#xf07b;";
                break;
        }
        return Html.fromHtml(result);
    }

    public static String getConvertTime(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        Date curDate = null;
        try {
            curDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("MMMM d, yyyy HH:mm a", Locale.US).format(curDate);
    }

    public static String getDayOfWeek(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date curDate = null;
        try {
            curDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("EEEE", Locale.US).format(curDate);
    }

    public static String getHour(String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        Date curDate = null;
        try {
            curDate = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("H", Locale.US).format(curDate);
    }
}
