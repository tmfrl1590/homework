package com.example.homework.entity

import jakarta.persistence.*

@Entity
class PaymentHistory(

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
}