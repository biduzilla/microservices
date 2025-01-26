package com.example.accounts.service.impl

import com.example.accounts.constants.AccountsConstants
import com.example.accounts.dto.CustomerDTO
import com.example.accounts.entity.Accounts
import com.example.accounts.entity.Customer
import com.example.accounts.exception.CustomerAlreadyExistsException
import com.example.accounts.exception.ResourceNotFoundException
import com.example.accounts.mapper.AccountsMapper
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

    override fun fetchAccount(mobileNumber: String): CustomerDTO {
        val customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException(
                resourceName = "Customer",
                fieldName = "mobileNumber",
                fieldValue = mobileNumber
            )
        }

        val accounts = accountsRepository.findByCustomerId(customer.customerId).orElseThrow {
            ResourceNotFoundException(
                resourceName = "Account",
                fieldName = "customerId",
                fieldValue = customer.customerId.toString()
            )
        }
        val dto = CustomerMapper.mapToCustomerDTO(customer)
        dto.accounts = AccountsMapper.mapToAccountsDTO(accounts)
        return dto
    }

    override fun updateAccount(customerDTO: CustomerDTO): Boolean {
        var isUpdated = false
        customerDTO.accounts?.let { accountsDto ->
            var accounts = accountsRepository.findById(accountsDto.accountNumber).orElseThrow {
                ResourceNotFoundException(
                    "Account",
                    "AccountNumber",
                    accountsDto.accountNumber.toString()
                )
            }
            accounts = AccountsMapper.mapToAccounts(accountsDto, accounts)
            accounts = accountsRepository.save(accounts)

            val customerId = accounts.customerId
            val customer = customerRepository.findById(customerId).orElseThrow {
                ResourceNotFoundException(
                    "Customer",
                    "CustomerID",
                    customerId.toString()
                )
            }
            CustomerMapper.mapToCustomer(customerDTO, customer)
            customerRepository.save(customer)
            isUpdated = true
        }

        return isUpdated
    }

    override fun deleteAccount(mobileNumber: String): Boolean {
        val customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow {
            ResourceNotFoundException(
                "Customer",
                "mobileNumber",
                mobileNumber
            )
        }
        accountsRepository.deleteByCustomerId(customer.customerId)
        customerRepository.deleteById(customer.customerId)
        return true
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