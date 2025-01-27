package com.example.cards.dto

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Data
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information"
)
@Data
data class ErrorResponseDTO(

    @Schema(
        description = "API path invoked by client"
    )
    var apiPath: String = "",

    @Schema(
        description = "Error code representing the error happened"
    )
    var errorCode: HttpStatus = HttpStatus.BAD_REQUEST,

    @Schema(
        description = "Error message representing the error happened"
    )
    var errorMessage: String = "",

    @Schema(
        description = "Time representing when the error happened"
    )
    var errorTime: LocalDateTime = LocalDateTime.now()
)
