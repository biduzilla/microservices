package com.example.accounts.service.client

import com.example.accounts.dto.LoansDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class LoansFallback : LoansFeignClient {
    override fun fetchLoanDetails(correlationId: String, mobileNumber: String): ResponseEntity<LoansDto> {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null)
    }
}