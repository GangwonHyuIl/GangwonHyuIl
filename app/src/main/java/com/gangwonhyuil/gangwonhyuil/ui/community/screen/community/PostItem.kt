package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import com.gangwonhyuil.gangwonhyuil.ui.community.entity.WriterInfo
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

data class PostItem(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: String,
    val content: String,
    val placeListCount: Int,
    val placeCount: Int,
) : Eigenvalue {
    override val viewType: Int get() = 0
    override val eigenvalue get() = id
}