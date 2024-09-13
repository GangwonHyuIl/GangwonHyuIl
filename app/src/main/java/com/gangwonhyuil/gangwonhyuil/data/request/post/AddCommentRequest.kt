package com.gangwonhyuil.gangwonhyuil.data.request.post

import com.google.gson.annotations.SerializedName

data class AddCommentRequest(
    @SerializedName("_post_idx")
    val postIdx: Int,
    @SerializedName("_user_idx")
    val userIdx: Int,
    @SerializedName("_comment_contents")
    val content: String,
)