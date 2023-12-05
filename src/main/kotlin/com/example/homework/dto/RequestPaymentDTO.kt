package com.example.homework.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min

data class RequestPaymentDTO(

    val shopName: String,

    @field:Min(value = 10000, message = "가격은 10000원 이상 입력해주세요.")
    @JsonProperty("price")
    private val _price: Int?,

    val isComplete: Boolean ,
){

    val price: Int
        get() = _price!!
}

data class CreateShopDTO(
    val shopName: String,
)