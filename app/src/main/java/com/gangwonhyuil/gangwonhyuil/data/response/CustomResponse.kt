package com.gangwonhyuil.gangwonhyuil.data.response

sealed interface CustomResponse {
    data class Success<T>(
        val data: T,
    ) : CustomResponse

    data class Failure(
        val e: Exception,
    ) : CustomResponse
}