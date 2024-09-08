package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLocal

import com.gangwonhyuil.gangwonhyuil.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class KakaoLocalInterceptor
    @Inject
    constructor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response =
            chain.proceed(
                chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_LOCAL_API_KEY}")
                    .build()
            )
    }