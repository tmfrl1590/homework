package com.example.homework.service

import com.example.homework.dto.CreateShopDTO
import com.example.homework.dto.RequestPaymentDTO
import com.example.homework.dto.RequestPaymentResponse
import com.example.homework.entity.*
import com.example.homework.repository.*
import com.example.homework.util.ResultResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.coroutines.suspendCoroutine

@Service
class HomeWorkService(
    private val userRepository: UserRepository,
    private val requestPaymentRepository: RequestPaymentRepository,
    private val shopRepository: ShopRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val redisRepository: RedisRepository,
) {

    // 1만원 결제요청
    @Transactional
    fun requestPayment(request: RequestPaymentDTO): String {
        val shop: Shop = shopRepository.findByShopName(request.shopName!!) ?: throw IllegalStateException(ResultResponse.NOT_EXIST_SHOP.message)
        val user: User = userRepository.findByEmail("tmfrl1570@naver.com")

        val entity = RequestPayment.fixture(
            requestPayment_id = null,
            shopName = request.shopName,
            price = request.price!!,
            shop = shop,
            user = user,
        )

        requestPaymentRepository.save(entity) // 저장(결제요청)

        // 저장 후 FCM 푸시로 클라이언트에게 푸시 알람 발송(클라이언트와 연동) - "가맹점으로부터 결제요청이 들어왔습니다"

        return ResultResponse.COMPLETE_PAYMENT_REQUEST.message
    }

    // 결제하기
    @Transactional
    fun doPayment(requestPayment_id: Long): String {

        // 중복요청인지 검사(Redis 에 저장되있는 값인지 체크)
        val result = redisRepository.findByIdOrNull(requestPayment_id)
        if(result != null) throw IllegalStateException(ResultResponse.DUPLICATION_REQUEST.message)

        // 중복요청이 아니면 Redis 저장(저장 후 10초 뒤에 키삭제 - 10초안에 같은 요청이면 중복요청으로 간주함)
        val redisEntity = RedisEntity(requestPayment_id)
        redisRepository.save(redisEntity)

        // requestPayment_id 에 해당하는 결제 요청을 조회해옴
        val requestPayment = requestPaymentRepository.findByIdOrNull(requestPayment_id) ?: throw IllegalStateException(ResultResponse.NOT_EXIST_PAYMENT_REQUEST.message)

        // 이미 완료된 결제 요청인지 체크
        if (requestPayment.isComplete) throw IllegalStateException(ResultResponse.COMPLETED_PAYMENT_REQUEST.message)

        // 결제를 하는 유저 정보 불러오기
        val user = userRepository.findByEmail("tmfrl1570@naver.com")

        // 금액이 부족하면 결제가 안되야함
        if (user.money < 10000) throw IllegalStateException(ResultResponse.FAIL_PAYMENT_MONEY.message)

        // 결제하기
        val paymentEntity = PaymentHistory.fixture(
            shop_id = requestPayment.shop.shop_id!!,
            price = requestPayment.price,
            isSuccess = true,
            requestPayment_id = requestPayment_id,
            user = user,
        )

        paymentHistoryRepository.save(paymentEntity) // 결제

        // 결제가 끝나면 결제요청의 완료처리, 돈 차감
        requestPayment.completeRequestPayment()
        user.updateMoney(10000)

        return ResultResponse.COMPLETE_PAYMENT.message
    }

    // 가맹점의 결제 요청 목록들
    @Transactional(readOnly = true)
    fun getRequestPayment(): List<RequestPaymentResponse> {
        val user = userRepository.findByEmail("tmfrl1570@naver.com")
        return requestPaymentRepository.findAll()
            .filter { it.user.user_id == user.user_id && !it.isComplete }
            .map {requestPayment -> RequestPaymentResponse.of(requestPayment)}
    }

    // 매장생성
    @Transactional
    fun createShop(request: CreateShopDTO): String {
        val shop = shopRepository.findByShopName(request.shopName)
        if (shop != null) throw IllegalStateException(ResultResponse.EXIST_SHOP.message)

        shopRepository.save(Shop.of(null, shopName = request.shopName))
        return ResultResponse.CREATE_SHOP.message
    }

}