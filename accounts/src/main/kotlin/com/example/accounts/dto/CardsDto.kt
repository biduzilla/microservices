package com.example.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import lombok.Data

@Schema(
    name = "Cards",
    description = "Schema to hold Card information"
)
@Data
data class CardsDto(
    @field:NotEmpty(message = "Mobile Number can not be a null or empty")
    @field:Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    @Schema(
        description = "Mobile Number of Customer", example = "4354437687"
    )
    var mobileNumber: String = "",

    @field:NotEmpty(message = "Card Number can not be a null or empty")
    @field:Pattern(regexp = "(^$|[0-9]{12})", message = "CardNumber must be 12 digits")
    @Schema(
        description = "Card Number of the customer", example = "100646930341"
    )
    var cardNumber: String = "",

    @field:NotEmpty(message = "CardType can not be a null or empty")
    @Schema(
        description = "Type of the card", example = "Credit Card"
    )
    var cardType: String = "",

    @field:Positive(message = "Total card limit should be greater than zero")
    @Schema(
        description = "Total amount limit available against a card", example = "100000"
    )
    var totalLimit: Int = 0,

    @field:PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
        description = "Total amount used by a Customer", example = "1000"
    )
    var amountUsed: Int = 0,

    @field:PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(
        description = "Total available amount against a card", example = "90000"
    )
    var availableAmount: Int = 0,
)
