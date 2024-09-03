package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import androidx.lifecycle.SavedStateHandle
import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostComment
import com.gangwonhyuil.gangwonhyuil.ui.community.model.PostDetail
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.GetPostCommentsUseCase
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.GetPostDetailUseCase
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.GetUserIdUseCase
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val EXTRA_POST_ID = "extra_post_id"

@HiltViewModel
class PostDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getUserId: GetUserIdUseCase,
        private val getPostDetailDetail: GetPostDetailUseCase,
        private val getPostComments: GetPostCommentsUseCase,
    ) : BaseViewModel() {
        private val postId = MutableStateFlow<Long?>(null)
        private val userId = MutableStateFlow<Long?>(null)

        private val _postDetail = MutableStateFlow<PostDetail?>(null)
        val postDetail = _postDetail.asStateFlow()

        private val _postComments = MutableStateFlow<List<PostComment>>(emptyList())
        val postComments = _postComments.asStateFlow()

        private val _isMyPost = MutableStateFlow(false)
        val isMyPost = _isMyPost.asStateFlow()

        init {
            with(savedStateHandle) {
                get<Long>(EXTRA_POST_ID)?.let { postId ->
                    Timber.d("postId: $postId")
                    this@PostDetailViewModel.postId.update { postId }
                }
            }

            viewModelScopeEH.launch {
                exceptions.collect { e ->
                    Timber.e(e.message)
                }
            }

            viewModelScopeEH.launch {
                userId.update { getUserId() }
            }

            // collect values
            viewModelScopeEH.launch {
                postId.collect { postId ->
                    if (postId != null) {
                        _postDetail.update { getPostDetailDetail(postId) }
                        _postComments.update { getPostComments(postId) }
                    }
                }
            }
            viewModelScopeEH.launch {
                combine(userId, postDetail) { userId, postDetail ->
                    userId != null && postDetail != null && userId == postDetail.writerInfo.id
                }.collect { isMyPost ->
                    _isMyPost.update { isMyPost }
                }
            }
        }
    }