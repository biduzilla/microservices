package com.example.accounts.service

import com.example.accounts.dto.CustomerDTO

interface IAccountsService {
    fun createAccount(customerDTO: CustomerDTO)
    fun fetchAccount(mobileNumber: String): CustomerDTO
    fun updateAccount(customerDTO: CustomerDTO): Boolean
    fun deleteAccount(mobileNumber:String):Boolean

}