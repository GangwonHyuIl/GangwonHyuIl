package com.gangwonhyuil.gangwonhyuil.ui.onboarding

import androidx.lifecycle.ViewModel
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginDTO
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginInterface
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        val kakaoLoginClient: KakaoLoginInterface
    ): BaseViewModel() {

    suspend fun fetchKakaoLogin() {
        try {
            val response =
                kakaoLoginClient.getKakaoId(KakaoLoginDTO("22222"))

            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    Timber.d("로그인 성공: ${response.body()}")
                }
            } else {
                Timber.d("로그인 실패 %s", response.errorBody()?.string())
            }
        } catch (e: Exception) {
            Timber.d("로그인 에러 %s", e.message)
        }
    }
}

