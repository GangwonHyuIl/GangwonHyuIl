package com.gangwonhyuil.gangwonhyuil.data.remote.weather

import com.gangwonhyuil.gangwonhyuil.data.response.weather.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDataSource {
    @GET("getVilageFcst")
    suspend fun getWeather(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRow: Int,
        @Query("pageNo") pageNo: Int,
        @Query("dataType") dataType: String = "JSON",
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    ): Response<Weather>
}