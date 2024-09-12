package com.gangwonhyuil.gangwonhyuil.data.remote.post

import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostDetailResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostDataSource {
    @GET("posts")
    suspend fun getPosts(): List<GetPostsResponse>

    @GET("post_detail")
    suspend fun getPostDetail(
        @Query("post_idx") postIdx: String,
    ): List<GetPostDetailResponse>
}