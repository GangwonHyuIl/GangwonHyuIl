package com.gangwonhyuil.gangwonhyuil.data.remote.post

import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostsResponse
import retrofit2.http.GET

interface PostDataSource {
    @GET("posts")
    suspend fun getPosts(): List<GetPostsResponse>
}