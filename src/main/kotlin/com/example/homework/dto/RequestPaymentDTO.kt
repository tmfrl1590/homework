package com.example.homework.dto

import com.example.homework.entity.RequestPayment
import com.example.homework.entity.Shop
import com.example.homework.entity.User

data class RequestPaymentDTO(
    val requestPayment_id: Long,
    val shopName: String,
    val price: Int,
    val isComplete: Boolean,
){

    fun toEntity(): RequestPayment = RequestPayment(
        requestPayment_id = requestPayment_id,
        shopName = shopName,
        price = price,
        isComplete = false,
        user = User.fixture(),
        shop = Shop.fixture(),
        //shop = Shop(1L, "패스트캠퍼스")
    )
}