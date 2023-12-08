package com.example.homework.entity

import jakarta.persistence.*

@Entity
class Payment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val paymentHistory_id: Long? = null,

    @Column(nullable = false)
    val shop_id: Long,

    @Column(nullable = false)
    val price: Int,

    @Column(nullable = false)
    val isSuccess: Boolean,

    @Column(nullable = false)
    val requestPayment_id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) {
    companion object {
        fun fixture(
            paymentHistory_id: Long? = null,
            shop_id: Long,
            price: Int,
            isSuccess: Boolean,
            requestPayment_id: Long,
            user: User? = null
        ): Payment{
            return Payment(
                paymentHistory_id = paymentHistory_id,
                shop_id = shop_id,
                price = price,
                isSuccess = isSuccess,
                requestPayment_id = requestPayment_id,
                user = user!!,
            )
        }
    }
}