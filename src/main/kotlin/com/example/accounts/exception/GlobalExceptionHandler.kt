package com.example.accounts.exception

import com.example.accounts.dto.ErrorResponseDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime


@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomerAlreadyExistsException::class)
    fun handleCustomerAlreadyExistsException(
        exception: CustomerAlreadyExistsException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorResponseDTO> {
        val errorResponseDTO: ErrorResponseDTO = ErrorResponseDTO(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            exception.message ?: "",
            LocalDateTime.now()
        )
        return ResponseEntity<ErrorResponseDTO>(errorResponseDTO, HttpStatus.BAD_REQUEST)
    }
}