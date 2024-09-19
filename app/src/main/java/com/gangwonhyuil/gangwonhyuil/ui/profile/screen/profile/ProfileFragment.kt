package com.gangwonhyuil.gangwonhyuil.ui.profile.screen.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentProfileBinding
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.setting.SettingActivity
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myPosts.MyPostsActivity
import com.gangwonhyuil.gangwonhyuil.ui.profile.screen.myReviews.MyReviewsActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}