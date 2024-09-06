package com.gangwonhyuil.gangwonhyuil.data.api

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.gangwonhyuil.gangwonhyuil.data.remote.tour.TourDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TourClient {
    private const val TOUR_BASE_URL = "http://apis.data.go.kr/B551011/KorService1/"
    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    private val tourRetrofit = Retrofit.Builder()
        .baseUrl(TOUR_BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    val tourNetWork: TourDataSource = tourRetrofit.create(TourDataSource::class.java)
}