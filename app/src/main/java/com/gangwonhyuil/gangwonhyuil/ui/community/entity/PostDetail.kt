package com.gangwonhyuil.gangwonhyuil.ui.community.entity

data class PostDetail(
    val id: Long,
    val writerInfo: WriterInfo,
    val timeStamp: String,
    val content: String,
    val placeList: List<PlaceList>,
    val comments: List<PostComment>,
) {
    data class PlaceList(
        val id: Long,
        val placeListName: String,
        val places: List<Place>,
    ) {
        data class Place(
            val id: Long,
            val category: PlaceCategory,
            val name: String,
            val address: String,
            val images: List<String> = emptyList(),
            val content: String = "",
        )
    }

    data class PostComment(
        val id: Long,
        val writerInfo: WriterInfo,
        val timeStamp: String,
        val content: String,
    )
}