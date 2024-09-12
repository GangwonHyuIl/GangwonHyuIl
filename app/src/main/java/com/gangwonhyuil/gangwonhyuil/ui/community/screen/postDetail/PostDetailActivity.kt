package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityPostDetailBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.reportDialog.ReportDialog
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.reportDialog.ReportableItem
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

interface OnReportDialogClickListener {
    fun onReportClick(
        reportableItem: ReportableItem,
        reason: String,
    )
}

interface OnPlaceDetailItemAdapterClickListener {
    fun onCommentReportClick(comment: PostDetailItem.CommentItem)

    fun onCommentDeleteClick(comment: PostDetailItem.CommentItem)
}

@AndroidEntryPoint
class PostDetailActivity :
    BaseActivity<ActivityPostDetailBinding>(),
    OnReportDialogClickListener,
    OnPlaceDetailItemAdapterClickListener {
    private val viewModel by viewModels<PostDetailViewModel>()

    private lateinit var postDetailItemAdapter: PostDetailItemAdapter

    override fun inflateBinding(inflater: LayoutInflater): ActivityPostDetailBinding = ActivityPostDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initObserveViewModel()
    }

    private fun initView() {
        initTopAppBar()
        initPostDetailRecyclerView()
        initAddCommentLayout()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            setNavigationOnClickListener {
                Timber.d("back button clicked")
                finish()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.post_detail_report -> {
                        viewModel.postContent?.let { postContent ->
                            showReportDialog(postContent)
                        }
                        true
                    }

                    R.id.post_detail_delete_post -> {
                        // TODO: delete post
                        Timber.d("delete post menu clicked")
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun showReportDialog(reportableItem: ReportableItem) {
        val reportDialog =
            ReportDialog(
                context = this,
                reportableItem = reportableItem,
                onClickListener = this
            )
        reportDialog.show()
    }

    private fun initPostDetailRecyclerView() {
        postDetailItemAdapter = PostDetailItemAdapter(this@PostDetailActivity)
        binding.rvPostDetail.adapter = postDetailItemAdapter
    }

    private fun initAddCommentLayout() {
        binding.bntAddComment.setOnClickListener {
            val comment = binding.etAddComment.text.toString()
            if (comment.isEmpty()) {
                Toast.makeText(this, "댓글을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.onAddComment(comment)

            val inputMethodManager =
                this@PostDetailActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            with(binding.etAddComment) {
                clearFocus()
                inputMethodManager.hideSoftInputFromWindow(
                    windowToken,
                    0
                )
            }
        }
    }

    private fun initObserveViewModel() {
        with(viewModel) {
            lifecycleScope.launch {
                isMyPost.collect { isMyPost ->
                    Timber.d("isMyPost: $isMyPost")
                    binding.topAppBar.run {
                        menu.clear()
                        inflateMenu(
                            if (isMyPost) {
                                R.menu.post_detail_my_post_top_app_bar
                            } else {
                                R.menu.post_detail_top_app_bar
                            }
                        )
                    }
                }
            }
            lifecycleScope.launch {
                postDetailItems.collect { postDetailItems ->
                    postDetailItemAdapter.submitList(postDetailItems)
                }
            }
            lifecycleScope.launch {
                userId.collect { userId ->
                    postDetailItemAdapter.userId = userId
                }
            }
            lifecycleScope.launch {
                postDetailState.collect {
                    when (it) {
                        PostDetailState.Idle -> {
                            // do nothing
                        }

                        PostDetailState.AddCommentSuccess -> {
                            binding.etAddComment.text.clear()
                            Toast
                                .makeText(
                                    this@PostDetailActivity,
                                    "댓글이 등록되었습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }

                        PostDetailState.AddCommentFail -> {
                            Toast
                                .makeText(
                                    this@PostDetailActivity,
                                    "댓글 등록에 실패했습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    }
                }
            }
        }
    }

    override fun onReportClick(
        reportableItem: ReportableItem,
        reason: String,
    ) {
        when (reportableItem) {
            is PostDetailItem.PostContent -> viewModel.reportPost(reportableItem.id, reason)
            is PostDetailItem.CommentItem -> viewModel.reportComment(reportableItem.id, reason)
        }
        Toast.makeText(this, "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onCommentReportClick(comment: PostDetailItem.CommentItem) {
        showReportDialog(comment)
    }

    override fun onCommentDeleteClick(comment: PostDetailItem.CommentItem) {
        // TODO: delete comment
        Timber.d("delete comment, comment: $comment")
    }

    companion object {
        fun getPostDetailActivityIntent(
            context: Context,
            postId: Long,
        ): Intent =
            Intent(context, PostDetailActivity::class.java).apply {
                putExtra(EXTRA_POST_ID, postId)
            }
    }
}