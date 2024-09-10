package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class KakaoLoginInterceptor
@Inject
constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain
                .request()
                .newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("apikey", "${BuildConfig.SUPABASE_API_KEY}")
                .addHeader("Content-Type", "application/json")
                .build()
        )
}