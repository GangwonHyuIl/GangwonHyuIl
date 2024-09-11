package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import com.gangwonhyuil.gangwonhyuil.data.interceptor.PostJsonRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoSignupInterface {
    @PostJsonRequest
    @POST("/rest/v1/rpc/register_user")
    suspend fun addKakaoIdSignup(
        @Body request: KakaoSignupDTO
    ): Response<KakaoSignupResponse>
}