package com.goloveschenko.weather.data.remote;

import com.goloveschenko.weather.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiClient {
    private static final String QUERY_NAME_APPID = "key";
    private static final String QUERY_VALUE_APPID = "a456c65a89b44bf8ac6101713161207";

    private static WeatherApiService service = null;

    public static WeatherApiService getClient() {
        if (service == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(QUERY_NAME_APPID, QUERY_VALUE_APPID).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit.create(WeatherApiService.class);
        }
        return service;
    }

}
