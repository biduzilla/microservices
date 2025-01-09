package com.example.accounts.dto

import lombok.Data

@Data
data class AccountsDTO(
    var accountNumber:Long = 0L,
    var accountType:String = "",
    var branchAddress:String = "",
)
