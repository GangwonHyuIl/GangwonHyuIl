package com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityPostDetailBinding
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.CommentItem.Companion.toCommentItem
import com.gangwonhyuil.gangwonhyuil.ui.community.screen.postDetail.PlaceItem.Companion.toPlaceItems
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {
    private val viewModel by viewModels<PostDetailViewModel>()

    private lateinit var placeItemAdapter: PlaceItemAdapter
    private lateinit var commentItemAdapter: CommentItemAdapter

    override fun inflateBinding(inflater: LayoutInflater): ActivityPostDetailBinding = ActivityPostDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getExtras()

        initView()
        initObserveViewModel()
    }

    private fun getExtras() {
        intent.extras?.let {
            val postId = it.getLong(EXTRA_POST_ID)
            Timber.d("postId: $postId")
        }
    }

    private fun initView() {
        initTopAppBar()
        initPlaceListRecyclerView()
        initPostCommentRecyclerView()
    }

    private fun initTopAppBar() {
        with(binding.topAppBar) {
            setNavigationOnClickListener {
                Timber.d("back button clicked")
                finish()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.report_post -> {
                        // TODO: report post
                        Timber.d("report post menu clicked")
                        true
                    }

                    R.id.edit_post -> {
                        // TODO: edit post
                        Timber.d("edit post menu clicked")
                        true
                    }

                    R.id.delete_post -> {
                        // TODO: delete post
                        Timber.d("delete post menu clicked")
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun initPlaceListRecyclerView() {
        placeItemAdapter = PlaceItemAdapter()
        binding.rvPlaceList.adapter = placeItemAdapter
    }

    private fun initPostCommentRecyclerView() {
        commentItemAdapter = CommentItemAdapter()
        binding.rvComment.adapter = commentItemAdapter
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
                postDetail.collect { postDetail ->
                    postDetail?.let {
                        Timber.d("postDetail: $it")
                        // set writer info
                        binding.ivPostWriterImage.load(it.writerInfo.profileImage)
                        binding.tvPostWriterName.text = it.writerInfo.name
                        binding.tvPostTime.text = it.timeStamp
                        // set content
                        binding.tvPostContent.text = it.content
                        // set place list
                        placeItemAdapter.submitList(toPlaceItems(it.placeList))
                    }
                }
            }
            lifecycleScope.launch {
                postComments.collect { postComments ->
                    Timber.d("postComments: $postComments")
                    commentItemAdapter.submitList(postComments.map { toCommentItem(it) })
                }
            }
        }
    }

    companion object {
        fun getPostDetailActivityIntent(
            context: Context,
            postId: Long,
        ): Intent {
            Timber.d("startPaymentActivity")
            return Intent(context, PostDetailActivity::class.java).apply {
                putExtra(EXTRA_POST_ID, postId)
            }
        }
    }
}