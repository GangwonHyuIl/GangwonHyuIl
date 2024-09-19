package com.gangwonhyuil.gangwonhyuil.data.response.profile

import com.google.gson.annotations.SerializedName

data class UserPostsResponse (
    @SerializedName("user_idx") val userIdx: Int,
    @SerializedName("posts") val posts: List<UserPostsResponseItem>?,
)