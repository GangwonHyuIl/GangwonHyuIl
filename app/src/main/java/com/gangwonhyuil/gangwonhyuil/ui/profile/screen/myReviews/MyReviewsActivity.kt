package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMyReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyReviewBinding
    private val viewModel by viewModels<MyReviewsViewModel>()


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
    }

    private fun initViewModelObserver() {
        lifecycleScope.launch {
            viewModel.reviewItems.collectLatest { reviewItems ->
//                po.submitList(postItems)
            }
        }
    }
}