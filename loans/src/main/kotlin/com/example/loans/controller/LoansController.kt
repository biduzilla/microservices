package com.example.loans.controller

import com.example.loans.constants.LoansConstants
import com.example.loans.dto.ErrorResponseDTO
import com.example.loans.dto.LoansDto
import com.example.loans.dto.ResponseDTO
import com.example.loans.service.ILoansService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(
    name = "CRUD REST APIs for Loans in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class LoansController(private val iLoansService: ILoansService) {

    private val logger = LoggerFactory.getLogger(LoansController::class.java)
    @Operation(
        summary = "Create Loan REST API",
        description = "REST API to create new loan inside EazyBank"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "HTTP Status CREATED"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [Content(
                    schema = Schema(implementation = ErrorResponseDTO::class)
                )]
            )
        ]
    )
    @PostMapping("/create", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createLoan(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        mobileNumber: String
    ): ResponseEntity<ResponseDTO> {
        iLoansService.createLoan(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ResponseDTO(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201))
    }

    @Operation(
        summary = "Fetch Loan Details REST API",
        description = "REST API to fetch loan details based on a mobile number"
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
                content = [Content(
                    schema = Schema(implementation = ErrorResponseDTO::class)
                )]
            )
        ]
    )
    @GetMapping("/fetch")
    fun fetchLoanDetails(
        @RequestHeader("eazybank-correlation-id")
        correlationId: String,
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        mobileNumber: String
    ): ResponseEntity<LoansDto> {
        logger.debug("eazyBank-correlation-id found: {} ", correlationId)
        val loansDto = iLoansService.fetchLoan(mobileNumber)
        return ResponseEntity.status(HttpStatus.OK).body(loansDto)
    }

    @Operation(
        summary = "Update Loan Details REST API",
        description = "REST API to update loan details based on a loan number"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            ApiResponse(
                responseCode = "417",
                description = "Expectation Failed"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [Content(
                    schema = Schema(implementation = ErrorResponseDTO::class)
                )]
            )
        ]
    )
    @PutMapping("/update")
    fun updateLoanDetails(@Valid @RequestBody loansDto: LoansDto): ResponseEntity<ResponseDTO> {
        val isUpdated = iLoansService.updateLoan(loansDto)
        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200))
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ResponseDTO(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE))
        }
    }

    @Operation(
        summary = "Delete Loan Details REST API",
        description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            ApiResponse(
                responseCode = "417",
                description = "Expectation Failed"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [Content(
                    schema = Schema(implementation = ErrorResponseDTO::class)
                )]
            )
        ]
    )
    @DeleteMapping("/delete")
    fun deleteLoanDetails(
        @RequestParam
        @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
        mobileNumber: String
    ): ResponseEntity<ResponseDTO> {
        val isDeleted = iLoansService.deleteLoan(mobileNumber)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDTO(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200))
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(ResponseDTO(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE))
        }
    }

}