package com.example.homework.entity

import com.example.homework.dto.RequestPaymentDTO
import jakarta.persistence.*

@Entity
class RequestPayment(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val requestPayment_id: Long? = null,

    @Column(nullable = false)
    val shopName: String,

    @Column(nullable = false)
    val price: Int = 10000,

    @Column(nullable = false)
    val isComplete: Boolean = false,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "shop_id")
    val shop: Shop
) {

    companion object{
        fun fixture(
            /*requestPayment_id: Long,
            shopName: String,
            price: Int,
            user: User,
            shop: Shop,*/
            requestPaymentDTO: RequestPaymentDTO
        ): RequestPayment{
            return RequestPayment(
                requestPayment_id = requestPaymentDTO.requestPayment_id,
                shopName = requestPaymentDTO.shopName,
                price = requestPaymentDTO.price,
                isComplete = false,
                user = User.fixture(),
                shop = Shop.fixture()
            )
        }

        fun toEntity(requestPaymentDTO: RequestPaymentDTO): RequestPayment = RequestPayment(
            requestPayment_id = requestPaymentDTO.requestPayment_id,
            shopName = requestPaymentDTO.shopName,
            price = requestPaymentDTO.price,
            isComplete = false,
            user = User.fixture(),
            shop = Shop.fixture(),
        )
    }



}