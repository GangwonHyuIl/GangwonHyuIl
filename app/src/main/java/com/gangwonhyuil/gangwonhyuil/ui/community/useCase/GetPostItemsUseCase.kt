package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.data.response.post.GetPostsResponse
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.WriterInfo
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.community.PostItem
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetPostItemsUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(): List<PostItem> =
            when (val customResponse = getPosts()) {
                is CustomResponse.Success<*> -> {
                    (customResponse.data as List<GetPostsResponse>).map {
                        PostItem(
                            id = it.postIdx.toLong(),
                            writerInfo =
                                WriterInfo(
                                    id = it.userIdx.toLong(),
                                    name = it.userNickname ?: "",
                                    profileImage = it.userProfileImage
                                ),
                            timeStamp =
                                LocalDateTime.parse(it.postCreatedAt.substring(0, 20)).format(
                                    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                                ),
                            content = it.postTitle,
                            placeListCount = it.categoryCount,
                            placeCount = it.placeCount
                        )
                    }
                }

                is CustomResponse.Failure -> {
                    Timber.e(customResponse.e)
                    emptyList()
                }
            }

        private suspend fun getPosts(): CustomResponse =
            try {
                val response = postDataSource.getPosts()
                CustomResponse.Success(response)
            } catch (e: Exception) {
                CustomResponse.Failure(e)
            }
    }