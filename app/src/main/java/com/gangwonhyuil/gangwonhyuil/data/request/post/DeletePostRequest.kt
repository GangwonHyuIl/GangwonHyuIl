package com.gangwonhyuil.gangwonhyuil.data.request.post

import com.google.gson.annotations.SerializedName

data class DeletePostRequest(
    @SerializedName("_post_idx")
    val postIdx: Int,
)