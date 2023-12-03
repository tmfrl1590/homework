package com.example.homework.service

import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.entity.Shop
import com.example.homework.repository.RequestPaymentRepository
import com.example.homework.repository.ShopRepository
import com.example.homework.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class RequestPaymentService(
    private val userRepository: UserRepository,
    private val requestPaymentRepository: RequestPaymentRepository,
    private val shopRepository: ShopRepository,
) {

    // 1만원 결제요청
    fun requestPayment(request: RequestPaymentDTO): String {
        //val user = userRepository.findById(1)
        /*val shop: Shop? = shopRepository.findByShop_id(1L)
        if(shop != null){
            println("가맹점 있음")
        }*/
        //shop.requestPayment(request)
        //val shop = shopRepository.findById(1L)


        val requestEntity = request.toEntity()
        println(request.toString())
        requestPaymentRepository.save(requestEntity)

        return "결제요청이 완료되었습니다."
    }
}