package com.example.homework.controller

import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.dto.CreateShopDTO
import com.example.homework.dto.RequestPaymentResponse
import com.example.homework.entity.Shop
import com.example.homework.service.HomeWorkService
import com.example.homework.util.BaseResponse
import com.example.homework.util.ResultResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeWorkController(
    private val requestPaymentService: HomeWorkService
) {

    // 1만원 결제 요청
    @PostMapping("/payment/request")
    fun requestPayment(@RequestBody @Valid request: RequestPaymentDTO): BaseResponse<Unit>{
        return BaseResponse(message = requestPaymentService.requestPayment(request))
    }

    // 가맹점의 결제 요청 목록들
    @GetMapping("/payment/request")
    fun getRequestPayment(): BaseResponse<List<RequestPaymentResponse>>{
        val resultList = requestPaymentService.getRequestPayment()
        return BaseResponse(
            data = resultList,
            message =
                if(resultList.isNotEmpty()) ResultResponse.SUCCESS_PAYMENT_REQUEST.message
                else ResultResponse.SUCCESS_PAYMENT_REQUEST_NODATA.message
        )
    }

    // 결제하기
    @PostMapping("/payment")
    fun doPayment(@RequestParam requestPayment_id: Long): BaseResponse<Unit>{
        return BaseResponse(message = requestPaymentService.doPayment(requestPayment_id))
    }

    // 가맹점 등록
    @PostMapping("/shop")
    fun createShop(@RequestBody request: CreateShopDTO): BaseResponse<Unit>{
        return BaseResponse(message = requestPaymentService.createShop(request))
    }
}