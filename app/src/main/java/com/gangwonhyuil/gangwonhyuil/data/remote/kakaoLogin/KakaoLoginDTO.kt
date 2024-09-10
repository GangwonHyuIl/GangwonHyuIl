package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import com.google.gson.annotations.SerializedName

data class KakaoLoginDTO (
    @SerializedName("input_user_id")
    val inputUserId: String
)