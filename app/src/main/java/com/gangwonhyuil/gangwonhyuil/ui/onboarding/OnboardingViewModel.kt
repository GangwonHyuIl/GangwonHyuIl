package com.gangwonhyuil.gangwonhyuil.ui.onboarding

import androidx.lifecycle.ViewModel
import com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin.KakaoLoginDTO
import com.gangwonhyuil.gangwonhyuil.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(): BaseViewModel() {

    fun loginRrequest() = KakaoLoginDTO(inputUserId = "22222")


}

