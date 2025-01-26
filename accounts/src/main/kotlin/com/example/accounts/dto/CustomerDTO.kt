package com.example.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import lombok.Data

@Data
@Schema(
    name = "Customer",
    description = "Schema to hold Customer and Account information"
)
data class CustomerDTO(

    @Schema(
        description = "Name of the customer", example = "Luiz Henrique"
    )
    @field:NotEmpty(message = "Name can not be a null or empty")
    @field:Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    var name:String = "",


    @Schema(
        description = "Email address of the customer", example = "luiz.devs@gmail.com"
    )
    @field:NotEmpty(message = "Email address can not be a null or empty")
    @field:Email(message = "Email address should be a valid value")
    var email:String = "",


    @Schema(
        description = "Mobile Number of the customer", example = "9345432123"
    )
    @field:Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    var mobileNumber:String = "",

    @Schema(
        description = "Account details of the Customer"
    )
    var accounts: AccountsDTO? = null
)
