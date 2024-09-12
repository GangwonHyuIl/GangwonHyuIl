package com.gangwonhyuil.gangwonhyuil.ui.home.office

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

data class PlaceItem(
    val id: Long,
    val name: String,
    val address: String,
    val image: String,
    val rating: String,
) : Eigenvalue {
    override val viewType: Int get() = 0
    override val eigenvalue
        get() = id
}