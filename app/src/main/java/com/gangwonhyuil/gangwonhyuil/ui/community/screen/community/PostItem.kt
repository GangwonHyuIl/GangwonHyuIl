package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import com.gangwonhyuil.gangwonhyuil.ui.community.model.WriterInfo
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.time.LocalDateTime

data class PostItem(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: LocalDateTime,
    val content: String,
    val placeListCount: Int,
    val placeCount: Int,
) : Eigenvalue {
    override val viewType: Int get() = 0
    override val eigenvalue get() = id
}