package com.gangwonhyuil.gangwonhyuil.data.remote.office

import com.gangwonhyuil.gangwonhyuil.data.response.office.OfficeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OfficeDataSource {
    @GET("share_office_list")
    suspend fun getOfficeList(
        @Query("share_office_location") location: String = "eq.1"
    ): Response<OfficeResponse>
}