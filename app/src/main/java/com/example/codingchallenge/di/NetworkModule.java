package com.example.codingchallenge.di;

import static com.example.codingchallenge.utils.Constants.BASE_URL;

import com.example.codingchallenge.data.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger-hilt module
 * Provides ApiService Singleton Object
 */
@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    ApiService getRetrofitApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(
                        new OkHttpClient.Builder()
                                .addNetworkInterceptor(loggingInterceptor)
                                .readTimeout(2, TimeUnit.MINUTES)
                                .writeTimeout(2, TimeUnit.MINUTES)
                                .connectTimeout(2, TimeUnit.MINUTES)
                                .build()
                )
                .build().create(ApiService.class);
    }
}


