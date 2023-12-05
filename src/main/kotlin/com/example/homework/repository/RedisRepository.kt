package com.example.homework.repository

import com.example.homework.entity.RequestPayment
import org.springframework.data.repository.CrudRepository

interface RedisRepository: CrudRepository<RequestPayment, Long>{
}