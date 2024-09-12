package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityPostDetailBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.reportPost.ReportPostDialog
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

interface OnReportPostDialogClickListener {
    fun onReportClick(
        postId: Long,
        reason: String,
    )
}

@AndroidEntryPoint
class PostDetailActivity :
    BaseActivity<ActivityPostDetailBinding>(),
    OnReportPostDialogClickListener {
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
                        startReportPostDialog()
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

    private fun startReportPostDialog() {
        viewModel.postContent?.let { postContent ->
            val reportPostDialog =
                ReportPostDialog(
                    context = this,
                    postContent = postContent,
                    onClickListener = this
                )
            reportPostDialog.show()
        }
    }

    private fun initPostDetailRecyclerView() {
        postDetailItemAdapter = PostDetailItemAdapter()
        binding.rvPostDetail.adapter = postDetailItemAdapter
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
        }
    }

    override fun onReportClick(
        postId: Long,
        reason: String,
    ) {
        viewModel.reportPost(postId, reason)
        Toast.makeText(this, "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show()
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