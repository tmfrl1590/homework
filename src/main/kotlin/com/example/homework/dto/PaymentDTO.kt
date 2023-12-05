package com.example.homework.dto

import com.example.homework.entity.RequestPayment

data class PaymentDTO(
    val shop_id: Long,

    val price: Int,

    val isSuccess: Boolean = true,

    val requestPayment_id: Long,
)