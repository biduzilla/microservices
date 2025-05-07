package com.example.accounts.mapper

import com.example.accounts.dto.CustomerDTO
import com.example.accounts.dto.CustomerDetailsDto
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

        fun mapToCustomerDetailsDto(customer: Customer, customerDetailsDto: CustomerDetailsDto): CustomerDetailsDto {
            return customerDetailsDto.apply {
                name = customer.name
                email = customer.email
                mobileNumber = customer.mobileNumber
            }
        }
        fun mapToCustomer(customerDTO: CustomerDTO): Customer {
            return Customer(
                name = customerDTO.name,
                email = customerDTO.email,
                mobileNumber = customerDTO.mobileNumber
            )
        }

        fun mapToCustomer(customerDTO: CustomerDTO, customer: Customer): Customer {
            customer.name = customerDTO.name
            customer.email = customerDTO.email
            customer.mobileNumber = customerDTO.mobileNumber
            return customer
        }
    }
}