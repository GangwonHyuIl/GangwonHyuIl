package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.profile

import com.gangwonhyuil.gangwonhyuil.data.response.profile.UserPostsResponse
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
class ProfileViewModel
@Inject
constructor(
    private val getMyReviewItems: MyReviewsUseCase,
    private val getMyPostItems: MyPostsUseCase,
    ) : BaseViewModel() {
    private val _reviewItems = MutableStateFlow<UserReviewsResponse?>(null)
    val reviewItems = _reviewItems.asStateFlow()
    private val _postItems = MutableStateFlow<UserPostsResponse?>(null)
    val postItems = _postItems.asStateFlow()

    init {
        viewModelScopeEH.launch {
            exceptions.collect { e ->
                Timber.e(e.message)
            }
        }

        viewModelScopeEH.launch {
            _postItems.update { getMyPostItems(1) }
        }

        viewModelScopeEH.launch {
            _reviewItems.update { getMyReviewItems(1) }
        }
    }
}