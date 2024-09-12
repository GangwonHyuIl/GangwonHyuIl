package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

data class ImageItem(
    val url: String,
) : Eigenvalue {
    override val viewType: Int = 0
    override val eigenvalue: Any = url

    companion object {
        fun toImageItems(urls: List<String>) = urls.map { url -> ImageItem(url) }
    }
}