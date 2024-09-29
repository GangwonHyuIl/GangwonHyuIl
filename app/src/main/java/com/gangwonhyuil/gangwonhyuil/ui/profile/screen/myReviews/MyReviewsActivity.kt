package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMyReviewBinding
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts.MyPostsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyReviewBinding
    private val viewModel by viewModels<MyReviewsViewModel>()
    private lateinit var adapter: MyReviewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiView()
        initViewModelObserver()
    }

    private fun intiView() {
        binding.tvBack.setOnClickListener {
            finish()
        }

        adapter = MyReviewsListAdapter()
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter
    }

    private fun initViewModelObserver() {
        lifecycleScope.launch {
            viewModel.reviewItems.collectLatest { reviewItems ->
                adapter.submitList(reviewItems?.posts)
            }
        }
    }
}