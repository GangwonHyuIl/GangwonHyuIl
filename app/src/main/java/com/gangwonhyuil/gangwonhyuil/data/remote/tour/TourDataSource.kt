package com.gangwonhyuil.gangwonhyuil.data.remote.tour

import com.gangwonhyuil.gangwonhyuil.data.response.tour.Tour
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val MOBILE_OS = "AND"
const val APP_NAME = "GanwonHyuil"
interface TourDataSource {
    @GET("areaBasedList1")
    suspend fun getAreaBasedList(
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("MobileOS") mobileOS: String = MOBILE_OS,
        @Query("MobileApp") mobileApp: String = APP_NAME,
        @Query("_type") type: String? = "JSON",
        @Query("arrange") arrange: String? = "O",
        @Query("contentTypeId") contentTypeId: String? = "39", //숙박:32 , 음식점:39
        @Query("areaCode") areaCode: String? = "32", //강원도:32
        @Query("sigunguCode") sigunguCode: String? = "1", //강릉:1, 동해:3, 속초:5, 영월:8, 원주:9, 춘천:13
        @Query("cat1") cat1: String? = "A05", //음식:A05
        @Query("cat2") cat2: String? = "A0502", //음식점:A0502
        @Query("cat3") cat3: String? = "A05020100", //한식:A05020100, 카페:A05020900
        @Query("serviceKey") serviceKey: String
    ): Response<Tour>
}