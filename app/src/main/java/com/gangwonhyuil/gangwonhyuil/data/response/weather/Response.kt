package com.gangwonhyuil.gangwonhyuil.data.response.weather

data class Response<T>(
    val header: Header,
    val body: T
)