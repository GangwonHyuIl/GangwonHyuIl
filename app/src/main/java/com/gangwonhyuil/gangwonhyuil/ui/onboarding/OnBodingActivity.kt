package com.gangwonhyuil.gangwonhyuil.ui.onboarding

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gangwonhyuil.gangwonhyuil.R
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityMainBinding
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityOnBodingBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class OnBodingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBodingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBodingBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_on_boding)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

    }
}