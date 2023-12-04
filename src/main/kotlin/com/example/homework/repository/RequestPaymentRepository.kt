package com.example.homework.repository

import com.example.homework.entity.RequestPayment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface RequestPaymentRepository: JpaRepository<RequestPayment, Long> {


}