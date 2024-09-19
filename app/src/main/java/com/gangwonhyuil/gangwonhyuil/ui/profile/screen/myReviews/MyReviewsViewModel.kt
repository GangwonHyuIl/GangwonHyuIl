package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews

import android.util.Log
import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserReviewsResponse
import com.gangwonhyuil.gangwonhyuil.ui.profile.useCase.MyPostsUseCase
import com.gangwonhyuil.gangwonhyuil.ui.profile.useCase.MyReviewsUseCase
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyReviewsViewModel
@Inject
constructor(
    private val getMyReviewItems: MyReviewsUseCase,
) : BaseViewModel() {
    private val _reviewItems = MutableStateFlow<UserReviewsResponse?>(null)
    val reviewItems = _reviewItems.asStateFlow()

    init {
        viewModelScopeEH.launch {
            exceptions.collect { e ->
                Timber.e(e.message)
            }
        }

        viewModelScopeEH.launch {
            _reviewItems.update { getMyReviewItems(1) }
            Timber.tag("seonghwi").d("das")

        }
    }
}