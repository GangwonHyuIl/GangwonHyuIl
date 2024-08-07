package com.gangwonhyuil.gangwonhyuil.presentation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentProfileBinding

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
            val intent = Intent(requireActivity(), MyReviewActivity::class.java)
            startActivity(intent)
        }
        binding.tvMyPostMore.setOnClickListener {
            val intent = Intent(requireActivity(), MyPostActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}