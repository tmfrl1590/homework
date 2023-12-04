package com.example.homework.repository

import com.example.homework.entity.PaymentHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentHistoryRepository: JpaRepository<PaymentHistory, Long> {

}