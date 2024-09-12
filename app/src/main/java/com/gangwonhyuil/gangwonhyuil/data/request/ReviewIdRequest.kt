package com.gangwonhyuil.gangwonhyuil.data.request

import com.google.gson.annotations.SerializedName

data class ReviewIdRequest(
    @SerializedName("_area_idx_list")
    val reviewId: List<Int>
)
