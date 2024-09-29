package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentProfileBinding
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts.MyPostsActivity
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews.MyReviewsActivity
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews.MyReviewsViewModel
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        initViewModelObserver()

        return root
    }

    private fun initView() {
        binding.imageSettings.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            startActivity(intent)
        }
        binding.tvMyReviewMore.setOnClickListener {
            val intent = Intent(requireActivity(), MyReviewsActivity::class.java)
            startActivity(intent)
        }
        binding.tvMyPostMore.setOnClickListener {
            val intent = Intent(requireActivity(), MyPostsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewModelObserver() {
        lifecycleScope.launch {
            viewModel.reviewItems.collectLatest { reviewItems ->
                binding.layoutItemReview.tvReviewScopeText.text = reviewItems?.posts?.get(0)?.reviewRating.toString()
                binding.layoutItemReview.edReviewText.text = reviewItems?.posts?.get(0)?.reviewContent.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.postItems.collectLatest { postItems ->
                binding.layoutItemPost.textPostTitle.text = postItems?.posts?.get(0)?.postTitle
                binding.layoutItemPost.textPostWriter.isInvisible = true
                binding.layoutItemPost.imageProfile.isInvisible = true
                binding.layoutItemPost.textListCount.text = "목록 ${postItems?.posts?.get(0)?.categoryCount}개"
                binding.layoutItemPost.textPlaceCount.text = "목록 ${postItems?.posts?.get(0)?.contentsCount}개"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}