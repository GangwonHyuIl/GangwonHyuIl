package com.gangwonhyuil.gangwonhyuil.data.remote.office

import com.gangwonhyuil.gangwonhyuil.data.interceptor.PostJsonRequest
import com.gangwonhyuil.gangwonhyuil.data.request.ReviewIdRequest
import com.gangwonhyuil.gangwonhyuil.data.response.office.OfficeResponse
import com.gangwonhyuil.gangwonhyuil.data.response.officeRating.OfficeReviews
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OfficeDataSource {
    @GET("share_office_list")
    suspend fun getOfficeList(
        @Query("share_office_location") location: String = "eq.1"
    ): Response<OfficeResponse>

    @PostJsonRequest
    @POST("rpc/get_area_reviews")
    suspend fun getOfficeRatingList(
        @Body idList: ReviewIdRequest
    ): Response<OfficeReviews>
}