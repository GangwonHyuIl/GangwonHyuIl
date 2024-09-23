package com.gangwonhyuil.gangwonhyuil.data.request.post

import com.google.gson.annotations.SerializedName

data class DeleteCommentRequest(
    @SerializedName("_comments_idx")
    val commentId: Int,
)