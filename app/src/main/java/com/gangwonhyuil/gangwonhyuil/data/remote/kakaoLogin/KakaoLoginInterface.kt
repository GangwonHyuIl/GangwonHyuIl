package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import com.gangwonhyuil.gangwonhyuil.data.interceptor.PostJsonRequest
import okhttp3.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoLoginInterface {
    @PostJsonRequest
    @POST("/rpc/login")
    suspend fun getKakaoId(
        @Body request: KakaoLoginDTO
    ): Response<KakaoLoginResponse>

}