package com.gangwonhyuil.gangwonhyuil.data.remote.post

import com.gangwonhyuil.gangwonhyuil.data.request.post.AddCommentRequest
import com.gangwonhyuil.gangwonhyuil.data.request.post.AddPostRequest
import com.gangwonhyuil.gangwonhyuil.data.request.post.DeleteCommentRequest
import com.gangwonhyuil.gangwonhyuil.data.request.post.DeletePostRequest
import com.gangwonhyuil.gangwonhyuil.data.response.post.AddCommentResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.AddPostResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.DeletePostResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostDetailResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostDataSource {
    @GET("posts")
    suspend fun getPosts(): List<GetPostsResponse>

    @GET("post_detail")
    suspend fun getPostDetail(
        @Query("post_idx") postIdx: String,
    ): List<GetPostDetailResponse>

    @POST("rpc/create_comment")
    suspend fun addComment(
        @Body addCommentRequest: AddCommentRequest,
    ): AddCommentResponse

    @POST("rpc/delete_comment")
    suspend fun deleteComment(
        @Body deleteCommentRequest: DeleteCommentRequest,
    )

    @POST("rpc/post_create")
    suspend fun addPost(
        @Body addPostRequest: AddPostRequest,
    ): AddPostResponse

    @POST("rpc/delete_post")
    suspend fun deletePost(
        @Body deletePostRequest: DeletePostRequest,
    ): DeletePostResponse
}