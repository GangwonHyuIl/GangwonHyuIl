package com.gangwonhyuil.gangwonhyuil.data.response.tour

data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)