package com.gangwonhyuil.gangwonhyuil.ui.profile.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.profile.ProfileDataSource
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostDetailResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostsResponse
import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserPostsResponse
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.WriterInfo
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.community.PostItem
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts.MyPosts
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts.MyPostsData
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MyPostsUseCase
@Inject
constructor(
    private val profileDataSource: ProfileDataSource,
) {
    suspend operator fun invoke(
        userIdx: Long,
    ): MyPostsData? =
        when (val customResponse = userPosts(userIdx)) {
            is CustomResponse.Success<*> -> {
                with(customResponse.data as UserPostsResponse) {
                    MyPostsData (
                        userIdx = userIdx.toInt(),
                        posts = posts?.map {
                            MyPosts (
                                postIdx = it.postIdx,
                                postTitle = it.postTitle,
                                categoryCount = it.categoryCount,
                                contentsCount = it.contentsCount
                            )
                        } ?: emptyList()
                    )
                }
            }
            is CustomResponse.Failure -> {
                Timber.e(customResponse.e)
                null
            }
        }

    private suspend fun userPosts(
        userIdx: Long,
    ): CustomResponse =
        try {
            val response =
                profileDataSource.getUserPosts("eq.$userIdx")
            CustomResponse.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            CustomResponse.Failure(e)
        }
}