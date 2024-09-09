package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLocal

import com.gangwonhyuil.gangwonhyuil.data.response.kakaoLocal.SearchViaKeywordResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoLocalDataSource {
    @GET("search/keyword.json")
    suspend fun searchViaKeyword(
        @Query("query") query: String,
        @Query("page") searchPage: Int,
    ): SearchViaKeywordResponse
}