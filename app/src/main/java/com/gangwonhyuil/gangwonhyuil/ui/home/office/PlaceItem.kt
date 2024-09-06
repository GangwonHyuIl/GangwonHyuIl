package com.gangwonhyuil.gangwonhyuil.ui.home.office

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

sealed interface PlaceItem : Eigenvalue {
    data class Place(
        val id: Long,
        val name: String,
        val address: String,
        val image: String,
//        val rating: String,
    ) : PlaceItem {
        override val viewType: Int get() = 0
        override val eigenvalue
            get() = id
    }

}