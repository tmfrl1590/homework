package com.example.homework.repository

import com.example.homework.entity.RedisEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RedisRepository: CrudRepository<RedisEntity, Long>{
}