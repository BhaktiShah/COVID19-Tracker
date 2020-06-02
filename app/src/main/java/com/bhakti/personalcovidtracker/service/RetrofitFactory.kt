package com.bhakti.personalcovidtracker.service

import com.bhakti.personalcovidtracker.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.COVID19_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //.setLogLevel(RestAdapter.LogLevel.FULL)
            .client(provideOkHttpClient()).build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val builder =
            OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        //.addInterceptor(AuthOkHttpInterceptor(context))
        return builder.build()
    }
}