package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse(
    @SerializedName("user_exists")
    val success: Boolean,
    @SerializedName("user_idx")
    val userIdx: Int
)
