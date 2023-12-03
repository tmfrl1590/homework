package com.example.homework.entity

import jakarta.persistence.*

@Entity
class PaymentHistory(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val paymentHistory_id: Long,

    @Column(nullable = false)
    val shop_id: String,

    @Column(nullable = false)
    val price: Int,

    @Column(nullable = false)
    val isSuccess: Boolean,

    @Column(nullable = false)
    val requestPayment_id: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
) {
}