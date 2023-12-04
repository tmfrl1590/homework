package com.example.homework.controller

import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.dto.CreateShopDTO
import com.example.homework.dto.RequestPaymentResponse
import com.example.homework.entity.RequestPayment
import com.example.homework.entity.User
import com.example.homework.service.RequestPaymentService
import com.example.homework.util.BaseResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RequestPaymentController(
    private val requestPaymentService: RequestPaymentService
) {

    // 1만원 결제 요청
    @PostMapping("/request/payment")
    fun requestPayment(@RequestBody @Valid request: RequestPaymentDTO): BaseResponse<Unit>{
        val msg = requestPaymentService.requestPayment(request)
        return BaseResponse(message = msg)
    }

    // 결제하기
    @PostMapping("/payment")
    fun doPayment(@RequestParam requestPayment_id: Long){
        requestPaymentService.doPayment(requestPayment_id)
    }

    // 가맹점의 결제 요청 목록들
    @GetMapping("/request/payment")
    fun getRequestPayment(): BaseResponse<List<RequestPaymentResponse>>{
        return BaseResponse(data = requestPaymentService.getRequestPayment())
    }

    // 가맹점 등록
    @PostMapping("/create/shop")
    fun createShop(@RequestBody request: CreateShopDTO){
        requestPaymentService.createShop(request)
    }

}