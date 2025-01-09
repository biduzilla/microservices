package com.example.accounts.mapper

import com.example.accounts.dto.CustomerDTO
import com.example.accounts.entity.Customer

class CustomerMapper {
    companion object {
        fun mapToCustomerDTO(customer: Customer): CustomerDTO {
            return CustomerDTO(
                name = customer.name,
                email = customer.email,
                mobileNumber = customer.mobileNumber
            )
        }

        fun mapToCustomer(customerDTO: CustomerDTO): Customer {
            return Customer(
                name = customerDTO.name,
                email = customerDTO.email,
                mobileNumber = customerDTO.mobileNumber
            )
        }
    }
}