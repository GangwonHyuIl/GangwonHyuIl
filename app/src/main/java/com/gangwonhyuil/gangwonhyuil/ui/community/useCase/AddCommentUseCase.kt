package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.request.post.AddCommentRequest
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import timber.log.Timber
import javax.inject.Inject

class AddCommentUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(
            postId: Long,
            userId: Long,
            content: String,
        ): Boolean =
            when (addComment(postId, userId, content)) {
                is CustomResponse.Success<*> -> true
                is CustomResponse.Failure -> false
            }

        private suspend fun addComment(
            postId: Long,
            userId: Long,
            content: String,
        ): CustomResponse =
            try {
                val response =
                    postDataSource.addComment(
                        AddCommentRequest(
                            postIdx = postId.toInt(),
                            userIdx = userId.toInt(),
                            content = content
                        )
                    )
                CustomResponse.Success(response)
            } catch (e: Exception) {
                Timber.e(e)
                CustomResponse.Failure(e)
            }
    }