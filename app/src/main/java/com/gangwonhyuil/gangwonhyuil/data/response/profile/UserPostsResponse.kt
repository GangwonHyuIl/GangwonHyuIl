package com.gangwonhyuil.gangwonhyuil.data.response.profile

import com.google.gson.annotations.SerializedName

data class UserPostsResponse (
    @SerializedName("user_idx") val userIdx: Int,
    @SerializedName("posts") val posts: List<UserPostsResponseItem>?,
) {
    data class UserPostsResponseItem (
        @SerializedName("post_idx")
        val postIdx: Int,
        @SerializedName("post_title")
        val postTitle: String,
        @SerializedName("category_count")
        val categoryCount: Int,
        @SerializedName("place_count")
        val contentsCount: Int,
    )
}