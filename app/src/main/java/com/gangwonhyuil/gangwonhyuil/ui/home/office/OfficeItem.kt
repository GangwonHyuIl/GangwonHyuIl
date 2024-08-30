package com.gangwonhyuil.gangwonhyuil.ui.home.office

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

sealed interface Office: Eigenvalue {
    data class OfficeItem(
        val id: Long,
        val name: String,
        val address: String,
        val image: String,
        val rating: String
    ) : Office {
        override val viewType: Int get() = 0
        override val eigenvalue
            get() = id
    }
}

