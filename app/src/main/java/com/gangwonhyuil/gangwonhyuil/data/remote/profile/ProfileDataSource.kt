package com.gangwonhyuil.gangwonhyuil.data.remote.profile

import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserPostsResponse
import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserReviewsResponse
import dagger.Provides
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

interface ProfileDataSource {
    @GET("user_posts")
    suspend fun getUserPosts(
        @Query("user_idx") userIdx: String,
    ): List<UserPostsResponse>

    @GET("user_reviews")
    suspend fun getUserReviews(
        @Query("user_idx") userIdx: String,
    ): List<UserReviewsResponse>
}