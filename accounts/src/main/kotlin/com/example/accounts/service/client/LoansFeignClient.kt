package com.example.accounts.service.client

import com.example.accounts.dto.LoansDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("loans")
interface LoansFeignClient {

    @GetMapping(value = ["/api/fetch"], consumes = ["application/json"])
    fun fetchLoanDetails(@RequestParam mobileNumber: String): ResponseEntity<LoansDto>
}