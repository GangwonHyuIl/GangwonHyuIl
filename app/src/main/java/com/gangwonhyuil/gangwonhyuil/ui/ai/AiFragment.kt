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
import com.gangwonhyuil.gangwonhyuil.R
//import com.gangwonhyuil.gangwonhyuil.data.remote.gemini.AiAdapter
//import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.Chating
import com.gangwonhyuil.gangwonhyuil.databinding.FragmentAiBinding
//import com.gangwonhyuil.gangwonhyuil.domain.repository.AiViewModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }
}