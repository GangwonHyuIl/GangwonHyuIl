package com.gangwonhyuil.gangwonhyuil.data.response.post

import com.google.gson.annotations.SerializedName

data class GetPostsResponse(
    @SerializedName("category_count")
    val categoryCount: Int,
    @SerializedName("place_count")
    val placeCount: Int,
    @SerializedName("post_created_at")
    val postCreatedAt: String,
    @SerializedName("post_idx")
    val postIdx: Int,
    @SerializedName("post_title")
    val postTitle: String,
    @SerializedName("user_idx")
    val userIdx: Int,
    @SerializedName("user_nickname")
    val userNickname: String?, // TODO: change property to not-null
    @SerializedName("user_profile_image")
    val userProfileImage: String?,
)