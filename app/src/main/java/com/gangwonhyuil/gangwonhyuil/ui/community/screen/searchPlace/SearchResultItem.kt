package com.gangwonhyuil.gangwonhyuil.ui.community.screen.searchPlace

import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

data class SearchResultItem(
    val id: String,
    val categoryGroup: String,
    val name: String,
    val roadAddress: String,
    val url: String,
) : Eigenvalue {
    override val viewType: Int = 0
    override val eigenvalue: Any = Unit
}