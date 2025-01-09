package com.example.accounts.service

import com.example.accounts.dto.CustomerDTO

interface IAccountsService {
    fun createAccount(customerDTO: CustomerDTO)

}