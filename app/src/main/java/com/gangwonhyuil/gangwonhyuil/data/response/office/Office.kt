package com.gangwonhyuil.gangwonhyuil.data.response.office

import com.google.gson.annotations.SerializedName

data class Office(
    @SerializedName("share_office_address") val address: String,
    @SerializedName("share_office_contact") val contact: String,
    @SerializedName("share_office_idx") val idx: Int,
    @SerializedName("share_office_image") val imageUrl: String? = "",
    @SerializedName("share_office_name") val name: String,
    @SerializedName("share_office_option") val options: String,
    @SerializedName("share_office_price") val price: String,
    @SerializedName("share_office_time") val businessHours: String,
    @SerializedName("share_office_url") val url: String,
    @SerializedName("average_rating") val rating: Float,
    @SerializedName("total_reviews") val numOfReview: Int
)