package com.example.accounts.controller

import com.example.accounts.constants.AccountsConstants
import com.example.accounts.dto.CustomerDTO
import com.example.accounts.dto.ResponseDTO
import com.example.accounts.service.IAccountsService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AccountsController(
    private val iAccountsService: IAccountsService
) {

    @PostMapping("/create")
    fun createAccount(@RequestBody data: CustomerDTO): ResponseEntity<ResponseDTO> {
        iAccountsService.createAccount(data)
        return ResponseEntity
            .status(
                HttpStatus.CREATED
            ).body(
                ResponseDTO(
                    statusCode = AccountsConstants.STATUS_201,
                    statusMsg = AccountsConstants.MESSAGE_201
                )
            )
    }
}