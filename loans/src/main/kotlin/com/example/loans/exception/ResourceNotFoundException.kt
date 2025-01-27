package com.example.loans.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    resourceName: String,
    fieldName: String,
    fieldValue: String
) : RuntimeException(
    String.format(
        String.format(
            "%s not found with the given input data %s : '%s'",
            resourceName,
            fieldName,
            fieldValue
        )
    )
)