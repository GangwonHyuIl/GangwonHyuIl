package com.gangwonhyuil.gangwonhyuil.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginDTO
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginInterface
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoSignupDTO
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoSignupInterface
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoSignupResponse
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        val kakaoLoginClient: KakaoLoginInterface,
        val kakaoSignupClient: KakaoSignupInterface
    ): BaseViewModel() {

        private val _isLogin = MutableLiveData<Boolean>(false)
        val isLogin: LiveData<Boolean> = _isLogin

    suspend fun fetchKakaoLogin(userId: String) {
        try {
            val response =
                kakaoLoginClient.getKakaoId(KakaoLoginDTO("$userId"))
            Log.d("sdc", "카카오 아이디 : $userId")

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Timber.d("로그인 성공: ${response.body()}")
                    _isLogin.value = true
                    Log.d("sdc", "유저 아이디: $userId")
                }
            } else {
                Timber.d("로그인 실패 %s", response.errorBody()?.string())
                fetchKakaoSignup(userId)
            }
        } catch (e: Exception) {
            Timber.d("로그인 에러 %s", e.message)
        }
    }

    suspend fun fetchKakaoSignup(userId: String) {
        try {
            val response =
                kakaoSignupClient.addKakaoIdSignup(KakaoSignupDTO("$userId", "안녕", "string"))

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Timber.d("회원가입 성공: ${response.body()}")
                    Log.d("sdc", "회원가입 성공: ${userId}")
                }
            } else {
                Timber.d("회원가입 실패 %s", response.errorBody()?.string())
            }
        } catch (e: Exception) {
            Timber.d("회원가입 에러 %s", e.message)
        }
    }
}

