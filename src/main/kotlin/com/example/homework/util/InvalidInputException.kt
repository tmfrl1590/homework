package com.example.homework.util

class InvalidInputException(
    val fieldName: String = "",
    message: String = "Invalid Input",
): RuntimeException(message)