package com.example.accounts.service.impl

import com.example.accounts.constants.AccountsConstants
import com.example.accounts.dto.CustomerDTO
import com.example.accounts.entity.Accounts
import com.example.accounts.entity.Customer
import com.example.accounts.exception.CustomerAlreadyExistsException
import com.example.accounts.mapper.CustomerMapper
import com.example.accounts.repository.AccountsRepository
import com.example.accounts.repository.CustomerRepository
import com.example.accounts.service.IAccountsService
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class AccountsServiceImpl(
    private val accountsRepository: AccountsRepository,
    private val customerRepository: CustomerRepository
) : IAccountsService {
    override fun createAccount(customerDTO: CustomerDTO) {
        val existingCustomer = customerRepository.findByMobileNumber(customerDTO.mobileNumber)

        if (existingCustomer.isPresent) {
            throw CustomerAlreadyExistsException(
                "Customer already registered with given mobileNumber ${customerDTO.mobileNumber}"
            )
        }

        var customer = CustomerMapper.mapToCustomer(customerDTO)
        customer = customerRepository.save(customer)
        accountsRepository.save(createNewAccount(customer))
    }

    private fun createNewAccount(customer: Customer): Accounts {
        val newAccount = Accounts()
        newAccount.customerId = customer.customerId
        val randomAccNumber: Long = 1000000000L + Random.nextInt(900000000)

        newAccount.accountNumber = randomAccNumber
        newAccount.accountType = AccountsConstants.SAVINGS
        newAccount.branchAddress = AccountsConstants.ADDRESS
        return newAccount
    }
}