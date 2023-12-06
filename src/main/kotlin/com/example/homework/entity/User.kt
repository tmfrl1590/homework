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
    var money: Int,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val requestPaymentList: MutableList<RequestPayment> = mutableListOf(),

    @OneToMany(mappedBy = "user")
    val paymentHistory: MutableList<PaymentHistory> = mutableListOf(),
) {

    fun updateMoney(price: Int){
        this.money -= price
    }

    companion object {
        fun of(
            user_id: Long?,
            email: String,
            fcmToken: String,
            money: Int
        ): User {
            return User(
                user_id = user_id,
                email = email,
                fcmToken = fcmToken,
                money = money,
            )
        }
    }

}