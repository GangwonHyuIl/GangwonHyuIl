package com.gangwonhyuil.gangwonhyuil.di

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLocal.KakaoLocalDataSource
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLocal.KakaoLocalInterceptor
import com.gangwonhyuil.gangwonhyuil.data.remote.tour.TourDataSource
import com.gangwonhyuil.gangwonhyuil.data.remote.wether.WeatherDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val WEATHER_BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
private const val TOUR_BASE_URL = "http://apis.data.go.kr/B551011/KorService1/"
private const val KAKAO_LOCAL_BASE_URL = "https://dapi.kakao.com/v2/local/"

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideWeatherDataSource(): WeatherDataSource = createRetrofit(WEATHER_BASE_URL).create(WeatherDataSource::class.java)

    @Singleton
    @Provides
    fun provideTourDataSource(): TourDataSource = createRetrofit(TOUR_BASE_URL).create(TourDataSource::class.java)

    @Singleton
    @Provides
    fun provideKakaoLocalDataSource(kakaoLocalInterceptor: KakaoLocalInterceptor): KakaoLocalDataSource =
        createRetrofit(
            baseUrl = KAKAO_LOCAL_BASE_URL,
            customInterceptor = kakaoLocalInterceptor
        ).create(
            KakaoLocalDataSource::class.java
        )

    private fun createRetrofit(
        baseUrl: String,
        customInterceptor: Interceptor? = null,
    ): Retrofit {
        val loggingInterceptor =
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
                .addNetworkInterceptor(loggingInterceptor)
                .apply {
                    if (customInterceptor != null) addInterceptor(customInterceptor)
                }.build()

        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}