package com.example.accounts.dto

import lombok.Data

@Data
data class ResponseDTO(
    var statusCode:String="",
    var statusMsg:String=""

)
