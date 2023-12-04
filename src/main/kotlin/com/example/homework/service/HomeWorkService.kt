package com.example.homework.service

import com.example.homework.dto.CreateShopDTO
import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.dto.RequestPaymentResponse
import com.example.homework.entity.PaymentHistory
import com.example.homework.entity.RequestPayment
import com.example.homework.entity.Shop
import com.example.homework.entity.User
import com.example.homework.repository.PaymentHistoryRepository
import com.example.homework.repository.RequestPaymentRepository
import com.example.homework.repository.ShopRepository
import com.example.homework.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HomeWorkService(
    private val userRepository: UserRepository,
    private val requestPaymentRepository: RequestPaymentRepository,
    private val shopRepository: ShopRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
) {

    // 1만원 결제요청
    fun requestPayment(request: RequestPaymentDTO): String {

        val shop: Shop? = shopRepository.findByShopName(request.shopName)
        val user: User? = userRepository.findByEmail("tmfrl1570@naver.com")

        val entity = RequestPayment.fixture(
            requestPayment_id = null,
            shopName = request.shopName,
            price = request.price,
            shop = shop,
            user = user
        )

        requestPaymentRepository.save(entity)

        // 저장 후 FCM 푸시로 클라이언트에게 푸시 알람 발송 - "가맹점으로부터 결제요청이 들어왔습니다"

        return "결제요청이 완료되었습니다."
    }

    // 결제하기
    @Transactional
    fun doPayment(requestPayment_id: Long): String {

        // requestPayment_id 에 해당하는 결제 요청을 조회해옴
        val requestPayment = requestPaymentRepository.findById(requestPayment_id).orElseThrow()

        // 이미 완료된 결제 요청인지 체크
        if (requestPayment.isComplete) {
            throw IllegalStateException("이미 결제가 완료된 요청사항입니다.")
        }

        // 결제를 하는 유저 정보 불러오기
        val user = userRepository.findByEmail("tmfrl1570@naver.com")

        // 금액이 부족하면 결제가 안되야함

        if (user!!.money < 10000) {
            throw IllegalStateException("금액이 부족해서 결제에 실패했습니다")
        }

        // 결제하기
        val paymentEntity = PaymentHistory.fixture(
            shop_id = requestPayment.shop.shop_id!!,
            price = requestPayment.price,
            isSuccess = true,
            requestPayment_id = requestPayment_id,
            user = user,
        )

        paymentHistoryRepository.save(paymentEntity)

        // 결제가 끝나면 완료처리
        requestPayment.completeRequestPayment()

        return "결제가 완료되었습니다."
    }

    // 가맹점의 결제 요청 목록들
    @Transactional(readOnly = true)
    fun getRequestPayment(): List<RequestPaymentResponse> {
        val user = userRepository.findByEmail("tmfrl1570@naver.com")
        return requestPaymentRepository.findAll().map { requestPayment -> RequestPaymentResponse.of(requestPayment) }
    }

    // 매장생성
    fun createShop(request: CreateShopDTO): String {
        val shop: Shop? = shopRepository.findByShopName(request.shopName)
        if(shop != null){
            throw IllegalStateException("이미 존재하는 매장의 이름입니다.")
        }
        shopRepository.save(Shop.fixture(null, shopName = request.shopName))
        return "매장생성이 완료되었습니다."
    }
}