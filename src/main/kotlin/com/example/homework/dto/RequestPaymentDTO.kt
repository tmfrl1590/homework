package com.example.homework.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class RequestPaymentDTO(

    @field:NotBlank(message = "매장 이름을 입력해주세요.")
    val shopName: String?,

    @field:Min(value = 10000, message = "가격은 10000원 이상 입력해주세요.")
    val price: Int?,

    val isComplete: Boolean ,
)

data class CreateShopDTO(
    val shopName: String,
)