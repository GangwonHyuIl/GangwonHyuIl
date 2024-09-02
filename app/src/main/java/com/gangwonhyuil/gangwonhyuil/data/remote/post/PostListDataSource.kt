package com.gangwonhyuil.gangwonhyuil.data.remote.post

data class PostListDataSource (
    val post: List<Post>?,
)

data class Post (
    val profileImage: String?,
    val writerName: String?,
    val postTitle: String?,
    val placeListCount: Int?,
    val placeCount: Int?,
)