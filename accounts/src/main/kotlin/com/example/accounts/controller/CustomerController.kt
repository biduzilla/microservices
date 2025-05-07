package com.example.accounts.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "REST API for Customers in EazyBank",
    description = "REST APIs in EazyBank to FETCH customer details"
)
@RestController
@RequestMapping(path=["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class CustomerController {
}