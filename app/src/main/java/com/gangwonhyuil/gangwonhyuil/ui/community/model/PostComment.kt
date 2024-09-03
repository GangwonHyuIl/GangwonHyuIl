package com.gangwonhyuil.gangwonhyuil.ui.community.model

import java.time.LocalDateTime

data class PostComment(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: LocalDateTime,
    val content: String,
)