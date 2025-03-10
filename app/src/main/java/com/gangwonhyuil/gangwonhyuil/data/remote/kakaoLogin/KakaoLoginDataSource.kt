package com.gangwonhyuil.gangwonhyuil.data.remote.kakaoLogin

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.gangwonhyuil.gangwonhyuil.ui.onboarding.OnboardingViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine

class KakaoLoginDataSource {

    private val client by lazy {
        UserApiClient.instance
    }

    fun isLoginUserInfo(): String {
        var userId = ""
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    "sdc", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )
                userId = user.id.toString()
            }
        }
        return userId
    }

    suspend fun login(context: Context): String {
        return if (client.isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context).accessToken
        } else {
            loginWithKakaoAccount(context).accessToken
        }
    }

    private suspend fun loginWithKakaoTalk(context: Context): OAuthToken = suspendCancellableCoroutine {
        client.loginWithKakaoTalk(context) { token, error ->
            error?.let {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
            }

            it.resumeWith(Result.success(token ?: return@loginWithKakaoTalk))
        }
    }

    private suspend fun loginWithKakaoAccount(context: Context): OAuthToken = suspendCancellableCoroutine { continuation ->
        client.loginWithKakaoAccount(context) { token, error ->
            error?.let {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoAccount
                }
            }

            continuation.resumeWith(Result.success(token ?: return@loginWithKakaoAccount))
        }
    }

    fun kakaoLogout() {
        // 로그아웃
        client.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun kakaoUnlink() {
        // 연결 끊기
        client.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            } else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }
}