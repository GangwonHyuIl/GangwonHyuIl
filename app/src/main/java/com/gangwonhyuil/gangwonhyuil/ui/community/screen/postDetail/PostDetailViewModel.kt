package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import androidx.lifecycle.SavedStateHandle
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PostComment
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PostDetail
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.PostDetailItem.Companion.toCommentItems
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.PostDetailItem.Companion.toPlaceItems
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

@Suppress("ktlint:standard:backing-property-naming")
@HiltViewModel
class PostDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getUserId: GetUserIdUseCase,
        private val getPostDetailDetail: GetPostDetailUseCase,
        private val getPostComments: GetPostCommentsUseCase,
    ) : BaseViewModel() {
        private val _postId = MutableStateFlow<Long?>(null)
        private val _userId = MutableStateFlow<Long?>(null)

        private val _postDetail = MutableStateFlow<PostDetail?>(null)
        private val _postComments = MutableStateFlow<List<PostComment>>(emptyList())
        private val _postDetailItems = MutableStateFlow<List<PostDetailItem>>(emptyList())
        val postDetailItems = _postDetailItems.asStateFlow()

        private val _isMyPost = MutableStateFlow(false)
        val isMyPost = _isMyPost.asStateFlow()

        init {
            with(savedStateHandle) {
                get<Long>(EXTRA_POST_ID)?.let { postId ->
                    Timber.d("postId: $postId")
                    this@PostDetailViewModel._postId.update { postId }
                }
            }

            viewModelScopeEH.launch {
                exceptions.collect { e ->
                    Timber.e(e.message)
                }
            }

            viewModelScopeEH.launch {
                _userId.update { getUserId() }
            }

            // collect values
            viewModelScopeEH.launch {
                _postId.collect { postId ->
                    if (postId != null) {
                        _postDetail.update { getPostDetailDetail(postId) }
                        _postComments.update { getPostComments(postId) }
                    }
                }
            }
            viewModelScopeEH.launch {
                combine(_postDetail, _postComments) { postDetail, postComments ->
                    postDetail?.let {
                        mutableListOf<PostDetailItem>().apply {
                            add(
                                PostDetailItem.PostContent(
                                    id = it.id,
                                    writerProfileImage = it.writerInfo.profileImage,
                                    writerName = it.writerInfo.name,
                                    timeStamp = it.timeStamp,
                                    content = it.content
                                )
                            )
                            addAll(toPlaceItems(it.placeList))
                            add(PostDetailItem.CommentHeader)
                            addAll(toCommentItems(postComments))
                        }
                    } ?: emptyList()
                }.collect { postDetailItems ->
                    _postDetailItems.update { postDetailItems }
                }
            }

            viewModelScopeEH.launch {
                combine(_userId, _postDetail) { userId, postDetail ->
                    userId != null && postDetail != null && userId == postDetail.writerInfo.id
                }.collect { isMyPost ->
                    _isMyPost.update { isMyPost }
                }
            }
        }
    }