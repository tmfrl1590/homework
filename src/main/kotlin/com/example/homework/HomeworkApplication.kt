package com.example.homework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class HomeworkApplication

fun main(args: Array<String>) {
    runApplication<HomeworkApplication>(*args)
}
