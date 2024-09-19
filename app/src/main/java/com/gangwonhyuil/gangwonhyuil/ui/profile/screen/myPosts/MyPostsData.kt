package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts
import com.gangwonhyuil.gangwonhyuil.util.base.Eigenvalue

data class MyPostsData (
    val userIdx: Int,
    val posts: List<MyPosts>
): Eigenvalue {
    override val viewType: Int
        get() = 0
    override val eigenvalue: Any
        get() = userIdx
}

data class MyPosts (
    val postIdx: Int,
    val postTitle: String,
    val categoryCount: Int,
    val contentsCount: Int,
)