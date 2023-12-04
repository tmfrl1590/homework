package com.example.homework.service

import com.example.homework.dto.CreateShopDTO
import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.dto.RequestPaymentResponse
import com.example.homework.entity.RequestPayment
import com.example.homework.entity.Shop
import com.example.homework.entity.User
import com.example.homework.repository.RequestPaymentRepository
import com.example.homework.repository.ShopRepository
import com.example.homework.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RequestPaymentService(
    private val userRepository: UserRepository,
    private val requestPaymentRepository: RequestPaymentRepository,
    private val shopRepository: ShopRepository,
) {

    // 1만원 결제요청
    fun requestPayment(request: RequestPaymentDTO): String {

        val shop: Shop? = shopRepository.findByShopName(request.shopName)
        //val user = userRepository.findById(1L).orElseThrow()
        val user: User? = userRepository.findByEmail("tmfrl1570@naver.com")

        val entity = RequestPayment.fixture(
            requestPayment_id = null,
            shopName = request.shopName,
            price = request.price,
            shop = shop,
            user = user
        )

        requestPaymentRepository.save(entity)

        return "결제요청이 완료되었습니다."
    }

    // 결제하기
    @Transactional
    fun doPayment(requestPayment_id: Long){

        // requestPayment_id 에 해당하는 결제 요청을 조회해옴
        val requestPayment = requestPaymentRepository.findById(requestPayment_id).orElseThrow()

        // 이미 완료된 결제 요청인지 체크
        if(requestPayment.isComplete){
            throw Exception("이미 결제가 완료된 요청사항입니다.")
        }

        // 결제하기


    }

    // 가맹점의 결제 요청 목록들
    @Transactional(readOnly = true)
    fun getRequestPayment(): List<RequestPaymentResponse> {
        val user = userRepository.findByEmail("tmfrl1570@naver.com")
        return requestPaymentRepository.findAll().map { requestPayment -> RequestPaymentResponse.of(requestPayment) }
    }

    // 매장생성
    fun createShop(request: CreateShopDTO){
        shopRepository.save(Shop.fixture(null, shopName = request.shopName))
    }
}