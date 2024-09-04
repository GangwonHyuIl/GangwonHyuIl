package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue
import java.net.URL

data class ImageItem(
    val url: URL,
) : Eigenvalue {
    override val viewType: Int = 0
    override val eigenvalue: Any = url

    companion object {
        fun toImageItems(urls: List<URL>) = urls.map { url -> ImageItem(url) }
    }
}