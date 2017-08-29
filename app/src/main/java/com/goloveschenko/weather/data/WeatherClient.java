package com.goloveschenko.weather.data;

import com.goloveschenko.weather.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherClient {
    private static final String QUERY_NAME_APPID = "key";
    private static final String QUERY_VALUE_APPID = "a456c65a89b44bf8ac6101713161207";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(QUERY_NAME_APPID, QUERY_VALUE_APPID).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

}
