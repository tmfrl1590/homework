package com.example.homework.util

data class BaseResponse<T>(
    val code: String = ResultCode.SUCCESS.name,
    val data: T? = null,
    val message: String? = null
)