package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMyPostBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint

class MyPostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPostBinding
    private val viewModel by viewModels<MyPostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPostBinding.inflate(layoutInflater)
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
            viewModel.postItems.collectLatest { postItems ->
//                po.submitList(postItems)
            }
        }
    }
}