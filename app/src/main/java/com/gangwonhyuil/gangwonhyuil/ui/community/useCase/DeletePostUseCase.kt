package com.gangwonhyuil.gangwonhyuil.ui.community.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.post.PostDataSource
import com.gangwonhyuil.gangwonhyuil.data.request.post.DeletePostRequest
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import timber.log.Timber
import javax.inject.Inject

class DeletePostUseCase
    @Inject
    constructor(
        private val postDataSource: PostDataSource,
    ) {
        suspend operator fun invoke(postId: Long): Boolean =
            when (deletePost(postId)) {
                is CustomResponse.Success<*> -> true
                is CustomResponse.Failure -> false
            }

        private suspend fun deletePost(postId: Long): CustomResponse =
            try {
                val response =
                    postDataSource.deletePost(
                        DeletePostRequest(postId.toInt())
                    )
                CustomResponse.Success(response)
            } catch (e: Exception) {
                Timber.e(e.message)
                CustomResponse.Failure(e)
            }
    }