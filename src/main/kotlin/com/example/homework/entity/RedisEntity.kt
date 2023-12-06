package com.example.homework.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("redis", timeToLive = 5)
data class RedisEntity(
    @Id
    val id: Long? = null,
)