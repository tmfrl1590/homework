package com.example.homework.repository

import com.example.homework.entity.RequestPayment
import com.example.homework.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

}