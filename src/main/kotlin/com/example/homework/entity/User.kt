package com.example.homework.entity

import jakarta.persistence.*

@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val user_id: Long? = null,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = true)
    val fcmToken: String,

    @Column(nullable = true)
    val money: Int,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val requestPaymentList: MutableList<RequestPayment> = mutableListOf(),

    @OneToMany(mappedBy = "user")
    val paymentHistory: MutableList<PaymentHistory> = mutableListOf(),
) {

    companion object {
        fun fixture(
            user_id: Long?,
            email: String = "tmfrl1570@naver.com",
            fcmToken: String = "asdasdadada",
            money: Int = 5000
        ): User{
            return User(
                user_id = user_id,
                email = email,
                fcmToken = fcmToken,
                money = money
            )
        }
    }
}