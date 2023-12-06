package com.example.homework.entity

import jakarta.persistence.*

@Entity
class RequestPayment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val requestPayment_id: Long? = null,

    @Column(nullable = false)
    val shopName: String,

    @Column(nullable = false)
    val price: Int = 10000,

    @Column(nullable = false)
    var isComplete: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    val shop: Shop
) {

    // 결제가 끝나면 완료처리
    fun completeRequestPayment(){
        this.isComplete = true
    }

    companion object{
        fun fixture(
            requestPayment_id: Long? = null,
            shopName: String,
            price: Int,
            user: User? = null,
            shop: Shop? = null,
        ): RequestPayment{
            return RequestPayment(
                requestPayment_id = requestPayment_id,
                shopName = shopName,
                price = price,
                isComplete = false,
                user = user!!,
                shop = shop!!
            )
        }
    }
}