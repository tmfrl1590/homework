package com.example.homework.dto

import com.example.homework.entity.RequestPayment

data class PaymentDTO(
    val shop_id: Long,

    val price: Int,

    val isSuccess: Boolean = true,

    val requestPayment_id: Long,
){
    /*companion object {
        fun of(requestPayment: RequestPayment): PaymentDTO{
            return PaymentDTO(
                shop_id = requestPayment.shop.shop_id!!,
                price = requestPayment.price,
                requestPayment_id = requestPayment.requestPayment_id!!,
            )
        }
    }*/
}