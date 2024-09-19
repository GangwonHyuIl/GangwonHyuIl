package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts

import android.util.Log
import com.gangwonhyuil.gangwonhyuil.ui.profile.useCase.MyPostsUseCase
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPostsViewModel
@Inject
constructor(
    private val getMyPostItems: MyPostsUseCase,
) : BaseViewModel() {
    private val _postItems = MutableStateFlow<MyPostsData?>(null)
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
    }
}