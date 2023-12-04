package com.example.homework.repository

import com.example.homework.entity.Shop
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShopRepository: JpaRepository<Shop, Long> {

    fun findByShopName(shopName: String?): Shop?
}