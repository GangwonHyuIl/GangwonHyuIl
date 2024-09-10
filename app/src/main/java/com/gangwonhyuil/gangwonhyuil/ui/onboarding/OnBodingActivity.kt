package com.gangwonhyuil.gangwonhyuil.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginDataSource
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityOnBodingBinding
import com.gangwonhyuil.gangwonhyuil.ui.MainActivity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import kotlinx.coroutines.launch

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
        var keyHash = Utility.getKeyHash(this)
        Log.d("keyHash", "$keyHash")

        KakaoSdk.init(this, "83ed3419c90df461922f69facba26d20")

        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                KakaoLoginDataSource().login(context = this@OnBodingActivity)
                KakaoLoginDataSource().isLoginUserInfo()
            }
        }
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                KakaoLoginDataSource().kakaoLogout()
            }
        }
        binding.btnUnlink.setOnClickListener {
            lifecycleScope.launch {
                KakaoLoginDataSource().kakaoUnlink()
            }
        }
        binding.btnStart.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}