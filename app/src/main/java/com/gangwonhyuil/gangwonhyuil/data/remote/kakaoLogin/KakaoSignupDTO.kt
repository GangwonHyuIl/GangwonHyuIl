package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import com.google.gson.annotations.SerializedName

data class KakaoSignupDTO(
    @SerializedName("_user_id")
    val inputUserId: String,
    @SerializedName("_user_nickname")
    val inputUserNickname: String,
    @SerializedName("_user_profile_image")
    val iputUserProfileImage: String
)