package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import androidx.lifecycle.SavedStateHandle
import com.gangwonhyuil.gangwonhyuil.ui.community.entity.PostDetail
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.PostDetailItem.Companion.toCommentItems
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.PostDetailItem.Companion.toPlaceItems
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.AddCommentUseCase
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.DeleteCommentUseCase
import com.gangwonhyuil.gangwonhyuil.ui.community.useCase.DeletePostUseCase
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
        private val addComment: AddCommentUseCase,
        private val deleteComment: DeleteCommentUseCase,
        private val deletePost: DeletePostUseCase,
    ) : BaseViewModel() {
        private val _postId = MutableStateFlow<Long?>(null)
        private val _userId = MutableStateFlow<Long?>(null)
        val userId = _userId.asStateFlow()

        private val _postDetail = MutableStateFlow<PostDetail?>(null)
        private val _postDetailItems = MutableStateFlow<List<PostDetailItem>>(emptyList())
        val postDetailItems = _postDetailItems.asStateFlow()
        val postContent: PostDetailItem.PostContent?
            get() =
                postDetailItems.value.find {
                    it is PostDetailItem.PostContent
                } as? PostDetailItem.PostContent

        private val _isMyPost = MutableStateFlow(false)
        val isMyPost = _isMyPost.asStateFlow()

        private val _postDetailState = MutableStateFlow<PostDetailState>(PostDetailState.Idle)
        val postDetailState = _postDetailState.asStateFlow()
        private val _postDetailToastMsg = MutableStateFlow<PostDetailToastMsg?>(null)
        val postDetailToastMsg = _postDetailToastMsg.asStateFlow()

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
                    }
                }
            }
            viewModelScopeEH.launch {
                _postDetail.collect { postDetail ->
                    val postDetailItems =
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
                                addAll(toCommentItems(it.comments))
                            }
                        } ?: emptyList()
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

        fun onAddComment(comment: String) {
            viewModelScopeEH.launch {
                if (addComment(
                        postId = _postId.value ?: return@launch,
                        userId = _userId.value ?: return@launch,
                        content = comment
                    )
                ) {
                    _postDetail.update { getPostDetailDetail(_postId.value!!) }
                    _postDetailState.update { PostDetailState.AddCommentSuccess }
                    _postDetailToastMsg.update { PostDetailToastMsg.ADD_COMMENT_SUCCESS }
                } else {
                    _postDetailToastMsg.update { PostDetailToastMsg.ADD_COMMENT_FAIL }
                }
            }
        }

        fun onDeleteComment(commentId: Long) {
            viewModelScopeEH.launch {
                if (deleteComment(commentId)) {
                    _postDetail.update { getPostDetailDetail(_postId.value!!) }
                    _postDetailToastMsg.update { PostDetailToastMsg.DELETE_COMMENT_SUCCESS }
                } else {
                    _postDetailToastMsg.update { PostDetailToastMsg.DELETE_COMMENT_FAIL }
                }
            }
        }

        fun reportComment(
            commentId: Long,
            reason: String,
        ) {
            // TODO: repost comment
            Timber.d("reportComment: $commentId, reason: $reason")
            _postDetailToastMsg.update { PostDetailToastMsg.REPORT_COMMENT_SUCCESS }
        }

        fun reportPost(
            postId: Long,
            reason: String,
        ) {
            // TODO: repost post
            Timber.d("reportPost: $postId, reason: $reason")
            _postDetailToastMsg.update { PostDetailToastMsg.REPORT_POST_SUCCESS }
        }

        fun deletePost() {
            viewModelScopeEH.launch {
                if (_postId.value == null) {
                    _postDetailToastMsg.update { PostDetailToastMsg.DELETE_POST_FAIL }
                    return@launch
                }
                if (deletePost(_postId.value!!)) {
                    _postDetailToastMsg.update { PostDetailToastMsg.DELETE_POST_SUCCESS }
                } else {
                    _postDetailToastMsg.update { PostDetailToastMsg.DELETE_POST_FAIL }
                }
            }
        }

        fun resetPostDetailState() {
            _postDetailState.update { PostDetailState.Idle }
        }

        fun resetPostDetailToastMsg() {
            _postDetailToastMsg.update { null }
        }
    }

sealed interface PostDetailState {
    data object Idle : PostDetailState

    data object AddCommentSuccess : PostDetailState
}

enum class PostDetailToastMsg(
    val message: String,
) {
    ADD_COMMENT_SUCCESS("댓글이 등록되었습니다."),
    ADD_COMMENT_FAIL("댓글 등록에 실패했습니다."),
    DELETE_COMMENT_SUCCESS("댓글이 삭제되었습니다."),
    DELETE_COMMENT_FAIL("댓글 삭제에 실패했습니다."),
    REPORT_COMMENT_SUCCESS("댓글 신고가 접수되었습니다."),
    REPORT_COMMENT_FAIL("댓글 신고 접수에 실패했습니다."),
    DELETE_POST_SUCCESS("게시글이 삭제되었습니다."),
    DELETE_POST_FAIL("게시글 삭제에 실패했습니다."),
    REPORT_POST_SUCCESS("게시글 신고가 접수되었습니다."),
    REPORT_POST_FAIL("게시글 신고 접수에 실패했습니다."),
}