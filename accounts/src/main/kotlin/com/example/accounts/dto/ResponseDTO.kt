package com.example.accounts.dto

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Data

@Schema(
    name = "Response",
    description = "Schema to hold successful response information"
)
@Data
data class ResponseDTO(
    @Schema(
        description = "Status code in the response"
    )
    var statusCode:String="",

    @Schema(
        description = "Status message in the response"
    )
    var statusMsg:String=""

)
