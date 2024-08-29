package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import java.net.URL

data class PostItem(
    val id: Long,
    val writerImage: URL,
    val writerName: String,
    val content: String,
    val placeListCount: Int,
    val placeCount: Int,
)