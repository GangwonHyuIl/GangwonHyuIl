package com.gangwonhyuil.gangwonhyuil.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginDataSource
import com.gangwonhyuil.gangwonhyuil.databinding.ActivityOnBodingBinding
import com.gangwonhyuil.gangwonhyuil.ui.MainActivity
import com.gangwonhyuil.gangwonhyuil.util.base.BaseActivity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBodingActivity : BaseActivity<ActivityOnBodingBinding>() {

    private val viewModel: OnboardingViewModel by viewModels()

    override fun inflateBinding(inflater: LayoutInflater): ActivityOnBodingBinding =
        ActivityOnBodingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.d("keyHash", "$keyHash")

        KakaoSdk.init(this, "83ed3419c90df461922f69facba26d20")

        buttonClick()

    }
    fun buttonClick() {
        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                viewModel.fetchKakaoLogin(KakaoLoginDataSource().isLoginUserInfo())
                KakaoLoginDataSource().login(context = this@OnBodingActivity)
//                KakaoLoginDataSource().isLoginUserInfo()
                viewModel.fetchKakaoSignup(KakaoLoginDataSource().isLoginUserInfo())
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
        binding.btnSignup.setOnClickListener {
            lifecycleScope.launch {
                viewModel.fetchKakaoSignup(KakaoLoginDataSource().isLoginUserInfo())
            }
        }
    }
}