package com.gangwonhyuil.gangwonhyuil.ui.community.model

data class PostComment(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: String,
    val content: String,
)