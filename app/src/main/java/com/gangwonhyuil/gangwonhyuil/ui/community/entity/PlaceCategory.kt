package com.gangwonhyuil.gangwonhyuil.ui.community.entity

enum class PlaceCategory(
    val category: String,
    val code: Int,
) {
    SHARED_OFFICE("공유 오피스", 0),
    RESTAURANT("식당", 1),
    CAFE("카페", 2),
    ACCOMODATION("숙박", 3),
}