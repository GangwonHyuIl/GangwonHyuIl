package com.gangwonhyuil.gangwonhyuil.data.response.weather

data class Body(
    val dataType: String,
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)