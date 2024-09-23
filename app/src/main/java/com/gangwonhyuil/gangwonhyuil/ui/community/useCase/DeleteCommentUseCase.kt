package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.request.post.DeleteCommentRequest
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import timber.log.Timber
import javax.inject.Inject

class DeleteCommentUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(commentId: Long): Boolean =
            when (deleteComment(commentId)) {
                is CustomResponse.Success<*> -> true
                is CustomResponse.Failure -> false
            }

        private suspend fun deleteComment(commentId: Long): CustomResponse =
            try {
                val response =
                    postDataSource.deleteComment(
                        DeleteCommentRequest(commentId.toInt())
                    )
                CustomResponse.Success(response)
            } catch (e: Exception) {
                Timber.e(e.message)
                CustomResponse.Failure(e)
            }
    }