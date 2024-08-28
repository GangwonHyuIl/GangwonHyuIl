package com.gangwonhyuil.gangwonhyuil.ui.community.entity

import retrofit2.http.Url
import java.net.URL

data class PostEntity(
    val id: Long,
    val writerImage: URL,
    val writerName: String,
    val content: String,
    val placeList: List<PlaceList>,
    val comments: List<Comment>
) {
    data class PlaceList(
        val id: Long,
        val placeListName: String,
        val places: List<Place>
    ) {
        data class Place(
            val category: PlaceCategory,
            val name: String,
            val address: String,
            val images: List<URL> = emptyList(),
            val content: String = ""
        ) {
            enum class PlaceCategory(val category: String) {
                SHARED_OFFICE("공유 오피스"),
                RESTAURANT("식당"),
                CAFE("카페"),
                ACCOMODATION("숙박"),
            }
        }
    }

    data class Comment(
        val id: Long,
        val writerImage: Url,
        val writerName: String,
    )
}