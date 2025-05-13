package com.example.cards.controller

import com.example.cards.constants.CardsConstants
import com.example.cards.dto.CardsDto
import com.example.cards.dto.ErrorResponseDTO
import com.example.cards.dto.ResponseDTO
import com.example.cards.service.ICardsService
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
    name = "CRUD REST APIs for Cards in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class CardsController(private val iCardsService: ICardsService) {
    private val logger = LoggerFactory.getLogger(CardsController::class.java)
    @Operation(
        summary = "Create Card REST API",
        description = "REST API to create new Card inside EazyBank"
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
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @PostMapping(
        "/create",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createCard(
        @Valid
        @RequestParam
        @Pattern(
            regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits"
        ) mobileNumber: String
    ): ResponseEntity<ResponseDTO> {
        iCardsService.createCard(mobileNumber)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body<ResponseDTO>(ResponseDTO(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201))
    }

    @Operation(
        summary = "Fetch Card Details REST API",
        description = "REST API to fetch card details based on a mobile number"
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
    @GetMapping("/fetch")
    fun fetchCardDetails(
        @RequestHeader("eazybank-correlation-id")
        correlationId: String,
        @RequestParam
        @Pattern(
            regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits"
        ) mobileNumber: String
    ): ResponseEntity<CardsDto> {
        logger.debug("eazyBank-correlation-id found: {} ", correlationId)
        val cardsDto = iCardsService.fetchCard(mobileNumber)
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto)
    }

    @Operation(
        summary = "Update Card Details REST API",
        description = "REST API to update card details based on a card number"
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
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @PutMapping("/update")
    fun updateCardDetails(
        @Valid @RequestBody cardsDto: CardsDto
    ): ResponseEntity<ResponseDTO> {
        val isUpdated = iCardsService.updateCard(cardsDto)
        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body<ResponseDTO>(ResponseDTO(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200))
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body<ResponseDTO>(ResponseDTO(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE))
        }
    }

    @Operation(
        summary = "Delete Card Details REST API",
        description = "REST API to delete Card details based on a mobile number"
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
    @DeleteMapping("/delete")
    fun deleteCardDetails(
        @RequestParam @Pattern(
            regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits"
        ) mobileNumber: String
    ): ResponseEntity<ResponseDTO> {
        val isDeleted = iCardsService.deleteCard(mobileNumber)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body<ResponseDTO>(ResponseDTO(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200))
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body<ResponseDTO>(ResponseDTO(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE))
        }
    }
}