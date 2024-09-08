package com.gangwonhyuil.gangwonhyuil.di

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.gangwonhyuil.gangwonhyuil.data.remote.tour.TourDataSource
import com.gangwonhyuil.gangwonhyuil.data.remote.wether.WeatherDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val WEATHER_BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
private const val TOUR_BASE_URL = "http://apis.data.go.kr/B551011/KorService1/"

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideWeatherDataSource(): WeatherDataSource = createRetrofit(WEATHER_BASE_URL).create(WeatherDataSource::class.java)

    @Singleton
    @Provides
    fun provideTourDataSource(): TourDataSource = createRetrofit(TOUR_BASE_URL).create(TourDataSource::class.java)

    private fun createRetrofit(baseUrl: String): Retrofit {
        val interceptor =
            HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

        val okHttpClient =
            OkHttpClient
                .Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build()

        return Retrofit
            .Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}