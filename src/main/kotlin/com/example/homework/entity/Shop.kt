package com.example.homework.entity

import jakarta.persistence.*

@Entity
class Shop(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    val shop_id: Long? = null,

    @Column(nullable = false)
    val shopName: String,

    @OneToMany(mappedBy = "shop", cascade = [CascadeType.ALL], orphanRemoval = true)
    val requestPaymentList: MutableList<RequestPayment> = mutableListOf(),
) {

    companion object {
        fun fixture(
            shop_id: Long?,
            shopName: String = "더드림",
        ): Shop {
            return Shop(
                shop_id = shop_id,
                shopName = shopName,
            )
        }
    }
}