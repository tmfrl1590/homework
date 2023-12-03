package com.example.homework.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    val user_id: Long,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = true)
    val fcmToken: String,

    @Column(nullable = true)
    val money: Int,

    @OneToMany(mappedBy = "user")
    val requestPaymentList: MutableList<RequestPayment> = mutableListOf(),

    @OneToMany(mappedBy = "user")
    val paymentHistory: MutableList<PaymentHistory> = mutableListOf(),
) {

    companion object {
        fun fixture(
            user_id: Long = 1L,
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