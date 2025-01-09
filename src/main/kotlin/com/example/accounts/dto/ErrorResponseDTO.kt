package com.example.accounts.dto

import lombok.Data
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Data
data class ErrorResponseDTO(
    var apiPath: String = "",
    var errorCode: HttpStatus = HttpStatus.BAD_REQUEST,
    var errorMessage: String = "",
    var errorTime: LocalDateTime = LocalDateTime.now()
)
