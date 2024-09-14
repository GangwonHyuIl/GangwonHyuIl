package com.gangwonhyuil.gangwonhyuil.ui.community.entity

data class AddPost(
    val writerId: Int,
    val postContent: String,
    val placeLists: List<PlaceList>,
) {
    data class PlaceList(
        val placeListName: String,
        val places: List<Place>,
    ) {
        data class Place(
            val placeName: String,
            val placeCategoryCode: Int,
            val placeAddress: String,
            val placeContent: String,
            val placeImages: List<String>,
        )
    }
}