package com.gangwonhyuil.gangwonhyuil.data.response.profile

import com.google.gson.annotations.SerializedName

data class UserReviewsResponseItem (
    @SerializedName("review_idx")
    val reviewIdx: Int,
    @SerializedName("area_idx")
    val areaIdx: Int,
    @SerializedName("review_rating")
    val reviewRating: Float,
    @SerializedName("review_content")
    val reviewContent: String,
    @SerializedName("created_at")
    val createdAt: String,
)