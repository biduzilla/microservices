package com.example.accounts.service.client

import com.example.accounts.dto.CardsDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("cards")
interface CardsFeignClient {
    @GetMapping(value = ["/api/fetch"], consumes = ["application/json"])
    fun fetchCardDetails(
        @RequestHeader("eazybank-correlation-id")
        correlationId: String, @RequestParam mobileNumber: String
    ): ResponseEntity<CardsDto>
}