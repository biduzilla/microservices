package com.example.accounts.service

import com.example.accounts.dto.CustomerDetailsDto

interface ICustomersService {
    fun fetchCustomerDetails(mobileNumber: String, correlationId: String): CustomerDetailsDto
}