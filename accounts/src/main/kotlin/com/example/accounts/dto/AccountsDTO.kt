package com.example.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import lombok.Data

@Schema(
    name = "Accounts",
    description = "Schema to hold Account information"
)
@Data
data class AccountsDTO(
    @Schema(
        description = "Account Number of Eazy Bank account", example = "3454433243"
    )
    @field:NotEmpty(message = "AccountNumber can not be a null or empty")
    @field:Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
    var accountNumber:Long = 0L,

    @Schema(
        description = "Account type of Eazy Bank account", example = "Savings"
    )
    @field:NotEmpty(message = "AccountType can not be a null or empty")
    var accountType:String = "",

    @Schema(
        description = "Eazy Bank branch address", example = "123 NewYork"
    )
    @field:NotEmpty(message = "BranchAddress can not be a null or empty")
    var branchAddress:String = "",
)
