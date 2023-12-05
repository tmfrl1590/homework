package com.example.homework.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun methodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Map<String, String>>>{
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage
            errors[fieldName] = errorMessage ?: "Not Exception Message"
        }
        return ResponseEntity(
            BaseResponse(
                code = ResultCode.ERROR.name,
                data = null,
                message = ex.message
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(InvalidInputException::class)
    protected fun invalidInputException(ex: InvalidInputException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errors = mapOf(ex.fieldName to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(
                code = ResultCode.ERROR.name,
                data = null,
                message = ex.message
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    protected fun defaultException(ex: Exception): ResponseEntity<BaseResponse<Map<String, String>>>{
        println("defaultException")
        val errors = mapOf("예외" to (ex.message ?: "Not Exception Message"))
        return ResponseEntity(
            BaseResponse(
                code = ResultCode.ERROR.name,
                data = null,
                message = ex.message
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}