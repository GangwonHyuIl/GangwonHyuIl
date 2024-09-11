package com.gangwonhyuil.gangwonhyuil.ui.ai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.data.remote.ai.AiDTO
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentAiBinding
import kotlinx.coroutines.launch

class AiFragment : Fragment() {

    private var _binding: FragmentAiBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AiAdapter
    private val viewModel : AiViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAiBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch {
                viewModel.sendMessage(binding.etSearch.text.toString())
            }
        }

        viewModel.chatList.observe(viewLifecycleOwner) {
            adapter.additem(it.toMutableList())
            Log.d("sdc", "Ai Chating = $it")
        }

        adapter = AiAdapter(requireContext())
        binding.rvAiChat.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}