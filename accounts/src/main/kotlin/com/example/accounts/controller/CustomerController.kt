package com.example.accounts.controller

import com.example.accounts.dto.CustomerDetailsDto
import com.example.accounts.dto.ErrorResponseDTO
import com.example.accounts.service.ICustomersService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.Pattern
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Tag(
    name = "REST API for Customers in EazyBank",
    description = "REST APIs in EazyBank to FETCH customer details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class CustomerController(private val iCustomersService: ICustomersService) {
    private val logger = LoggerFactory.getLogger(CustomerController::class.java)

    @Operation(
        summary = "Fetch Customer Details REST API",
        description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/fetchCustomerDetails")
    fun fetchCustomerDetails(
        @RequestHeader("eazybank-correlation-id")
        correlationId: String,
        @RequestParam @Pattern(
            regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits"
        ) mobileNumber: String
    ): ResponseEntity<CustomerDetailsDto?>? {
        logger.debug("eazyBank-correlation-id found: {} ", correlationId)
        val customerDetailsDto: CustomerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber,correlationId)
        return ResponseEntity.status(HttpStatus.OK).body<CustomerDetailsDto>(customerDetailsDto)
    }
}