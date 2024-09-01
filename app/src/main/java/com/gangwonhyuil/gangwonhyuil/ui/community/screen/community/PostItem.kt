package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.net.URL

data class PostItem(
    val id: Long,
    val writerImage: URL,
    val writerName: String,
    val content: String,
    val placeListCount: Int,
    val placeCount: Int,
) : Eigenvalue {
    override val viewType: Int get() = 0
    override val eigenvalue get() = id
}