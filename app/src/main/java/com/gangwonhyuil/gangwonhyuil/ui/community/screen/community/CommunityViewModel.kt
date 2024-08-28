package com.gangwonhyuil.gangwonhyuil.ui.community.screen.community

import com.gangwonhyuil.gangwonhyuil.ui.BaseViewModel
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.GetPostItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getPostItems: GetPostItemsUseCase
) : BaseViewModel() {
    private val _postItems = MutableStateFlow<List<PostItem>>(emptyList())
    val postItems = _postItems.asStateFlow()

    init {
        viewModelScopeEH.launch {
            _postItems.update { getPostItems() }
        }
    }
}