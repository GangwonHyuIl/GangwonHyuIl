package com.gangwonhyuil.gangwonhyuil.ui.community.entity

data class PostComment(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: String,
    val content: String,
)