package com.gangwonhyuil.gangwonhyuil.data.response.post

import com.google.gson.annotations.SerializedName

data class GetPostDetailResponse(
    @SerializedName("post_idx")
    val postIdx: Int,
    @SerializedName("user_idx")
    val writerIdx: Int,
    @SerializedName("user_nickname")
    val writerName: String,
    @SerializedName("user_profile_image")
    val writerProfileImage: String?,
    @SerializedName("post_created_at")
    val postCreatedAt: String,
    @SerializedName("post_title")
    val postTitle: String,
    @SerializedName("post_category")
    val placeLists: List<PlaceList>?,
    @SerializedName("post_comments")
    val postComments: List<PostComment>?,
) {
    data class PlaceList(
        @SerializedName("category_idx")
        val placeListIdx: Int,
        @SerializedName("category_name")
        val placeListName: String,
        @SerializedName("place_list")
        val places: List<Place>,
    ) {
        data class Place(
            @SerializedName("place_idx")
            val placeIdx: Int,
            @SerializedName("category_idx")
            val placeCategoryCode: Int,
            @SerializedName("place_title")
            val placeTitle: String,
            @SerializedName("place_address")
            val placeAddress: String,
            @SerializedName("place_images")
            val placeImages: List<PlaceImage>?,
            @SerializedName("place_detail")
            val placeContent: String?,
        ) {
            data class PlaceImage(
                @SerializedName("image_url")
                val imageUrl: String,
            )
        }
    }

    data class PostComment(
        @SerializedName("comments_idx")
        val commentIdx: Int,
        @SerializedName("user_idx")
        val writerIdx: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("comment_contents")
        val content: String,
    )
}