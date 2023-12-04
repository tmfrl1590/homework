package com.example.homework.dto

import com.example.homework.entity.RequestPayment

data class RequestPaymentResponse(
    val requestPayment_id: Long,
    val shopName: String,
    val price: Int,
    val isComplete: Boolean
){
    companion object {
        fun of(requestPayment: RequestPayment): RequestPaymentResponse{
            return RequestPaymentResponse(
                requestPayment_id = requestPayment.requestPayment_id!!,
                shopName = requestPayment.shopName,
                price = requestPayment.price,
                isComplete = requestPayment.isComplete,
            )
        }
    }
}