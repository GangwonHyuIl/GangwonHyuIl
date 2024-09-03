package com.gangwonhyuil.gangwonhyuil.ui.community.model

import java.net.URL

data class PostDetail(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: String,
    val content: String,
    val placeList: List<PlaceList>,
) {
    data class PlaceList(
        val id: Long,
        val placeListName: String,
        val places: List<Place>,
    ) {
        data class Place(
            val category: PlaceCategory,
            val name: String,
            val address: String,
            val images: List<URL> = emptyList(),
            val content: String = "",
        )
    }
}