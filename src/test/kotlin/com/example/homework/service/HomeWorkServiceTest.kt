package com.example.homework.service

import com.example.homework.dto.RequestPaymentResponse
import com.example.homework.entity.RequestPayment
import com.example.homework.entity.Shop
import com.example.homework.entity.User
import com.example.homework.repository.*
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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
    @DisplayName("가맹점의 결제 요청 목록들")
    fun getRequestPayment(){
        // given - 결제요청
        val entity = RequestPayment.of(
            requestPayment_id = null,
            shopName = shop.shopName,
            price = 10000,
            shop = shop,
            user = user,
        )
        requestPaymentRepository.save(entity)

        // when
        val resultList = requestPaymentRepository.findAll()
            .filter { it.user.user_id == user.user_id && !it.isComplete }
            .map {requestPayment -> RequestPaymentResponse.of(requestPayment)}

        // then
        assertThat(resultList).hasSize(1)
        assertThat(resultList[0].price).isEqualTo(10000)
        assertThat(resultList[0].shopName).isEqualTo("더드림")
    }

    @Test
    @DisplayName("1만원 결제요청 성공한다.")
    fun requestPaymentSuccess(){
        // given
        val entity = RequestPayment.of(
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
    @DisplayName("1만원 이외 금액의 결제요청은 실패한다.")
    fun requestPaymentFail(){
        // given
        val entity = RequestPayment.of(
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
        assertThat(resultRequest[0].price).isNotEqualTo(10001)
        assertThat(resultRequest[0].price).isNotEqualTo(9999)
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