package com.gangwonhyuil.gangwonhyuil.ui.community.screen.addPlace

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

sealed interface ImageItem : Eigenvalue {
    data object AddImage : ImageItem {
        override val viewType: Int = ImageViewType.ADD_IMAGE.type
        override val eigenvalue: Any = Unit
    }

    data class Image(
        val imageUrl: String,
    ) : ImageItem {
        override val viewType: Int = ImageViewType.IMAGE.type
        override val eigenvalue: Any = imageUrl
    }
}