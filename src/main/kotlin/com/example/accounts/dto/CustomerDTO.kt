package com.example.accounts.dto

import lombok.Data

@Data
data class CustomerDTO(
    var name:String = "",
    var email:String = "",
    var mobileNumber:String = "",
)
