package com.gangwonhyuil.gangwonhyuil.data.request.post

import com.google.gson.annotations.SerializedName

data class AddPostRequest(
    @SerializedName("_user_idx")
    val writerId: Int,
    @SerializedName("_post_title")
    val postContent: String,
    @SerializedName("_category_list")
    val placeLists: List<PlaceList>,
    @SerializedName("_post_comments")
    val postComments: List<Unit>, // always empty
) {
    data class PlaceList(
        @SerializedName("category_name")
        val placeListName: String,
        @SerializedName("place_list")
        val places: List<Place>,
        @SerializedName("user_idx")
        val writerId: Int,
    ) {
        data class Place(
            @SerializedName("place_title")
            val placeName: String,
            @SerializedName("place_type")
            val placeCategoryCode: Int,
            @SerializedName("place_address")
            val placeAddress: String,
            @SerializedName("place_detail")
            val placeContent: String,
            @SerializedName("place_images")
            val placeImages: List<PlaceImage>,
        ) {
            data class PlaceImage(
                @SerializedName("image_url")
                val imageUrl: String,
            )
        }
    }
}