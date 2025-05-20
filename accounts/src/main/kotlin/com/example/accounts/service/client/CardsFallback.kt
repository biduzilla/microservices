package com.example.accounts.service.client

import com.example.accounts.dto.CardsDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class CardsFallback : CardsFeignClient {
    override fun fetchCardDetails(correlationId: String, mobileNumber: String): ResponseEntity<CardsDto> {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null)
    }
}