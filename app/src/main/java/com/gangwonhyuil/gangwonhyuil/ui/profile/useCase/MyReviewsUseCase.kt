package com.gangwonhyuil.gangwonhyuil.ui.profile.useCase

import com.gangwonhyuil.gangwonhyuil.data.remote.profile.ProfileDataSource
import com.gangwonhyuil.gangwonhyuil.data.response.CustomResponse
import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserReviewsResponse
import timber.log.Timber
import javax.inject.Inject

class MyReviewsUseCase
@Inject
constructor(
    private val profileDataSource: ProfileDataSource,
) {
    suspend operator fun invoke(
        userIdx: Long,
    ): UserReviewsResponse? =
        when (val customResponse = userReviews(userIdx)) {
            is CustomResponse.Success<*> -> {
                    customResponse.data as UserReviewsResponse
            }
            is CustomResponse.Failure -> {
                Timber.e(customResponse.e)
                null
            }
        }

    private suspend fun userReviews(
        userIdx: Long,
    ): CustomResponse =
        try {
            val response =
                profileDataSource.getUserReviews("eq.$userIdx")
            CustomResponse.Success(response)
        } catch (e: Exception) {
            Timber.e(e)
            CustomResponse.Failure(e)
        }
}