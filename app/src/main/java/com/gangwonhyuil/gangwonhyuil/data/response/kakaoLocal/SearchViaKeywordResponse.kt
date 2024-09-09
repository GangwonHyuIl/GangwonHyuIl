package com.gangwonhyuil.gangwonhyuil.data.response.kakaoLocal

import com.google.gson.annotations.SerializedName

data class SearchViaKeywordResponse(
    val meta: Meta,
    val documents: List<Document>,
) {
    data class Meta(
        @SerializedName("total_count")
        val totalCount: Int,
        @SerializedName("pageable_count")
        val pageableCount: Int,
        @SerializedName("is_end")
        val isEnd: Boolean,
    )

    data class Document(
        val id: String,
        @SerializedName("place_name")
        val placeName: String,
        @SerializedName("category_name")
        val categoryName: String,
        val phone: String,
        @SerializedName("road_address_name")
        val roadAddressName: String,
        @SerializedName("place_url")
        val placeUrl: String,
    )
}