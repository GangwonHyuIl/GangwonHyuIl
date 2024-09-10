package com.gangwonhyuil.gangwonhyuil.data.response.office

import com.google.gson.annotations.SerializedName

data class OfficeResponseItem(
    val offices: List<Office>,
    @SerializedName("share_office_location")
    val location: Int
)