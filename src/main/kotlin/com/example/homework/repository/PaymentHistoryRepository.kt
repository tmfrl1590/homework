package com.example.homework.repository

import com.example.homework.entity.Payment
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentHistoryRepository: JpaRepository<Payment, Long> {

}