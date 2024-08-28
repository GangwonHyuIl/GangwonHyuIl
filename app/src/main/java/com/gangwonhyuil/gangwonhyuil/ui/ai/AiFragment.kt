package com.gangwonhyuil.gangwonhyuil.ui.ai

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.BuildConfig
import com.gangwonhyuil.gangwonhyuil.data.remote.Ai.AiDTO
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentAiBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AiFragment : Fragment() {

    private var _binding: FragmentAiBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AiAdapter
    private val viewModel : AiViewModel by viewModels()
    private val chatList : MutableList<AiDTO> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch {
                start11()
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.rightchatList.observe(viewLifecycleOwner) {
            adapter.additem(it.toMutableList())
        }

        viewModel.leftchatList.observe(viewLifecycleOwner){
            adapter.additem(it.toMutableList())
        }

        adapter = AiAdapter(requireContext())
        binding.rvAiChat.adapter = adapter
    }

    private suspend fun start11() {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.0-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.GEMINI_API_KEY
        )

//        val prompt = "Write a story about an AI and magic"
        val prompt = binding.etSearch.text.toString()
        val rightcurrent = LocalDateTime.now()
        val rightformatter = DateTimeFormatter.ofPattern("HH시 mm분")
        val rightformatted = rightcurrent.format(rightformatter)

//        binding.tvInput.text = prompt
//        binding.tvTime.text = formatted.toString()
        Log.d("sdc", rightformatted.toString())
        val rightchating = AiDTO(prompt, rightformatted.toString())
//        chatList.add(Chating(prompt, formatted.toString()))
        viewModel.addRightChating(rightchating)


        val response = generativeModel.generateContent(prompt)
        val leftcurrent = LocalDateTime.now()
        val leftformatter = DateTimeFormatter.ofPattern("HH시 mm분")
        val leftformatted = leftcurrent.format(leftformatter)
        val leftchating = AiDTO(response.text.toString(), leftformatted.toString())
        viewModel.addLeftChating(leftchating)

//        binding.tvOutput.text = response.text.toString()


        Log.d("sdc", response.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}