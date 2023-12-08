package com.example.homework.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class RequestPaymentDTO(

    @field:NotBlank(message = "매장 이름을 입력해주세요.")
    val shopName: String?,

    @field:Min(value = 10000, message = "결제요청은 10000원만 가능합니다.")
    @field:Max(value = 10000, message = "결제요청은 10000원만 가능합니다.")
    val price: Int?,

    val isComplete: Boolean ,
)

data class CreateShopDTO(
    val shopName: String,
)