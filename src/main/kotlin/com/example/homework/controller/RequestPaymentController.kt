package com.example.homework.controller

import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.service.RequestPaymentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RequestPaymentController(
    private val requestPaymentService: RequestPaymentService
) {

    // 1만원 결제 요청
    @PostMapping("/request/payment")
    fun requestPayment(@RequestBody request: RequestPaymentDTO){
        val msg = requestPaymentService.requestPayment(request)
        println(msg)
    }

}