package com.example.homework.entity

import com.example.homework.dto.RequestPaymentDTO
import jakarta.persistence.*

@Entity
class RequestPayment(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val requestPayment_id: Long,

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

}