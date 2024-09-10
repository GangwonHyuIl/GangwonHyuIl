package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import okhttp3.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoLoginInterface {
    @POST("/rpc/login")
    suspend fun getKakaoId(
        @Body request: KakaoLoginDTO)

}