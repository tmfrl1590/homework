package com.example.homework.service

import com.example.homework.entity.PaymentHistory
import com.example.homework.entity.RequestPayment
import com.example.homework.entity.Shop
import com.example.homework.entity.User
import com.example.homework.repository.*
import com.example.homework.util.ResultResponse
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class HomeWorkServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val requestPaymentRepository: RequestPaymentRepository,
    private val shopRepository: ShopRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val redisRepository: RedisRepository,
) {

    lateinit var shop: Shop
    lateinit var user: User

    @AfterEach
    fun clean(){
        userRepository.deleteAll()
        requestPaymentRepository.deleteAll()
        shopRepository.deleteAll()
        paymentHistoryRepository.deleteAll()
        redisRepository.deleteAll()
    }

    @BeforeEach
    fun createUserShop(){
        shop = Shop.of(null, shopName = "더드림")
        user = User.of(null, "tmfrl1570@naver.com", "asdasd", 10000)
        shopRepository.save(shop)
        userRepository.save(user)
    }

    @Test
    @DisplayName("1만원 결제 요청에 대한 결제하기 성공")
    fun doPaymentSuccess(){
        // given
        val entity = RequestPayment.fixture(
            requestPayment_id = null,
            shopName = shop.shopName,
            price = 10000,
            shop = shop,
            user = user,
        )
        val requestPayment = requestPaymentRepository.save(entity)

        // when
        val resultRequestPayment = requestPaymentRepository.findByIdOrNull(requestPayment.requestPayment_id) ?: throw IllegalStateException(ResultResponse.NOT_EXIST_PAYMENT_REQUEST.message)

        // 이미 완료된 결제 요청인지 체크
        if (resultRequestPayment.isComplete) throw IllegalStateException(ResultResponse.COMPLETED_PAYMENT_REQUEST.message)

        // 금액이 부족하면 결제가 안되야함
        if (user.money < 10000) throw IllegalStateException(ResultResponse.FAIL_PAYMENT_MONEY.message)

        // 결제하기
        val paymentEntity = PaymentHistory.fixture(
            shop_id = resultRequestPayment.shop.shop_id!!,
            price = resultRequestPayment.price,
            isSuccess = true,
            requestPayment_id = resultRequestPayment.requestPayment_id!!,
            user = user,
        )

        paymentHistoryRepository.save(paymentEntity) // 결제

        // 결제가 끝나면 결제요청의 완료처리, 돈 차감
        resultRequestPayment.completeRequestPayment()
        user.updateMoney(10000)

        // then
        val resultPaymentHistory = paymentHistoryRepository.findAll()
        assertThat(resultPaymentHistory).hasSize(1)


    }

    @Test
    @DisplayName("1만원 결제요청 성공한다.")
    fun requestPayment(){
        // given
        val entity = RequestPayment.fixture(
            requestPayment_id = null,
            shopName = shop.shopName,
            price = 10000,
            shop = shop,
            user = user,
        )

        // when
        requestPaymentRepository.save(entity) // 저장(결제요청)

        // then
        val resultRequest = requestPaymentRepository.findAll()
        assertThat(resultRequest).hasSize(1)
        assertThat(resultRequest[0].shopName).isEqualTo("더드림")
        assertThat(resultRequest[0].price).isEqualTo(10000)
        assertThat(resultRequest[0].user.email).isEqualTo("tmfrl1570@naver.com")

    }

    @Test
    @DisplayName("매장생성이 정상 동작한다.")
    fun createShop(){
        // given
        //val shop = Shop.fixture(null, shopName = "더드림")

        // when
        //shopRepository.save(shop)

        // then
        val resultShop = shopRepository.findAll()
        assertThat(resultShop).hasSize(1)
        assertThat(resultShop[0].shopName).isEqualTo("더드림")
    }
}