package com.gangwonhyuil.gangwonhyuil.data.response.officeRating

import com.google.gson.annotations.SerializedName

data class officeRatingResponse (
    @SerializedName("area_idx")
    val id: Int,
    @SerializedName("total_reviews")
    val numOfReviews: Int,
    @SerializedName("average_rating")
    val rating: Number
)