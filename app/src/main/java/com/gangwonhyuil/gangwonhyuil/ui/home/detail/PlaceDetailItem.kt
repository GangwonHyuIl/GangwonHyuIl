package com.gangwonhyuil.gangwonhyuil.ui.home.detail

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

sealed interface PlaceDetailItem : Eigenvalue {
    data class PlaceInfo(
        val id: Long,
        val title: String,
        val businessHour: String,
        val address: String,
        val phone: String,
//        val rating: String,
    ) : PlaceDetailItem {
        override val viewType: Int get() = PlaceDetailViewType.PLACE_CONTENT.type
        override val eigenvalue
            get() = id
    }

    data class PlaceImage(
        val id: Long,
        val image: String,
//        val rating: String,
    ) : PlaceDetailItem {
        override val viewType: Int get() = PlaceDetailViewType.PLACE_IMAGE.type
        override val eigenvalue
            get() = id
    }
}