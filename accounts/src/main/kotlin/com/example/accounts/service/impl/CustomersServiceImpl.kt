package com.example.accounts.service.impl

import com.example.accounts.dto.CustomerDetailsDto
import com.example.accounts.dto.LoansDto
import com.example.accounts.exception.ResourceNotFoundException
import com.example.accounts.mapper.AccountsMapper
import com.example.accounts.mapper.CustomerMapper
import com.example.accounts.repository.AccountsRepository
import com.example.accounts.repository.CustomerRepository
import com.example.accounts.service.ICustomersService
import com.example.accounts.service.client.CardsFeignClient
import com.example.accounts.service.client.LoansFeignClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CustomersServiceImpl(
    private val accountsRepository: AccountsRepository,
    private val customerRepository: CustomerRepository,
    private val cardsFeignClient: CardsFeignClient,
    private val loansFeignClient: LoansFeignClient
) : ICustomersService {
    override fun fetchCustomerDetails(mobileNumber: String,correlationId:String): CustomerDetailsDto {
        val customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        }

        val accounts = accountsRepository.findByCustomerId(customer.customerId).orElseThrow {
            ResourceNotFoundException("Account", "customerId", customer.customerId.toString())
        }

        val customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, CustomerDetailsDto())
        customerDetailsDto.accountsDto = AccountsMapper.mapToAccountsDTO(accounts)

        val loansDtoResponseEntity: ResponseEntity<LoansDto> = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber)
        loansDtoResponseEntity.body?.let {
            customerDetailsDto.loansDto = it
        }
        val cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber)
        cardsDtoResponseEntity.body?.let {
            customerDetailsDto.cardsDto = it
        }

        return customerDetailsDto
    }
}
