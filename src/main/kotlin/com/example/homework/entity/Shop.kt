package com.example.homework.entity

import com.example.homework.dto.RequestPaymentDTO
import jakarta.persistence.*

@Entity
class Shop(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    val shop_id: Long? = null,

    @Column(nullable = false)
    val shopName: String,

    @OneToMany(mappedBy = "shop")
    val requestPaymentList: MutableList<RequestPayment> = mutableListOf(),
) {

    companion object {
        fun fixture(
            shop_id: Long? = null,
            shopName: String = "패스트캠퍼스",
        ): Shop {
            return Shop(
                shop_id = shop_id,
                shopName = shopName,
            )
        }
    }

    fun requestPayment(requestPaymentDTO: RequestPaymentDTO) {
        this.requestPaymentList.add(
            RequestPayment(
                shopName = requestPaymentDTO.shopName,
                price = requestPaymentDTO.price,
                isComplete = false,
                user = User.fixture(),
                shop = Shop.fixture(),
                requestPayment_id = 1L
            )
        )
    }
}