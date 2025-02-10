package com.example.accounts.services

import com.example.accounts.constants.AccountsConstants
import com.example.accounts.dto.CustomerDTO
import com.example.accounts.entity.Accounts
import com.example.accounts.entity.Customer
import com.example.accounts.exception.CustomerAlreadyExistsException
import com.example.accounts.exception.ResourceNotFoundException
import com.example.accounts.mapper.AccountsMapper
import com.example.accounts.repository.AccountsRepository
import com.example.accounts.repository.CustomerRepository
import com.example.accounts.service.impl.AccountsServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class AccountsServiceImplTest {

    @Mock
    private lateinit var accountsRepository: AccountsRepository

    @Mock
    private lateinit var customerRepository: CustomerRepository

    @InjectMocks
    private lateinit var accountsService: AccountsServiceImpl

    private lateinit var customerDTO: CustomerDTO
    private lateinit var customer: Customer
    private lateinit var accounts: Accounts

    @BeforeEach
    fun setUp() {
        // Dados de teste
        customerDTO = CustomerDTO(
            name = "John Doe",
            email = "john.doe@example.com",
            mobileNumber = "1234567890"
        )

        customer = Customer(
            customerId = 1L,
            name = "John Doe",
            email = "john.doe@example.com",
            mobileNumber = "1234567890"
        )

        accounts = Accounts(
            accountNumber = 1000000001L,
            customerId = 1L,
            accountType = AccountsConstants.SAVINGS,
            branchAddress = AccountsConstants.ADDRESS
        )
    }

    @Test
    @DisplayName("Deve criar uma conta com sucesso")
    fun testCreateAccount() {
        // Configuração do mock
        `when`(customerRepository.findByMobileNumber(customerDTO.mobileNumber)).thenReturn(Optional.empty())
        `when`(customerRepository.save(any(Customer::class.java))).thenReturn(customer)
        `when`(accountsRepository.save(any(Accounts::class.java))).thenReturn(accounts)

        // Execução do teste
        accountsService.createAccount(customerDTO)

        // Verificações
        verify(customerRepository, times(1)).findByMobileNumber(customerDTO.mobileNumber)
        verify(customerRepository, times(1)).save(any(Customer::class.java))
        verify(accountsRepository, times(1)).save(any(Accounts::class.java))
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar conta com número de telefone já existente")
    fun testCreateAccount_CustomerAlreadyExists() {
        // Configuração do mock
        `when`(customerRepository.findByMobileNumber(customerDTO.mobileNumber)).thenReturn(Optional.of(customer))

        // Execução e verificação da exceção
        val exception = assertThrows(CustomerAlreadyExistsException::class.java) {
            accountsService.createAccount(customerDTO)
        }

        assertEquals(
            "Customer already registered with given mobileNumber ${customerDTO.mobileNumber}",
            exception.message
        )
    }

    @Test
    @DisplayName("Deve buscar uma conta com sucesso")
    fun testFetchAccount() {
        // Configuração do mock
        `when`(customerRepository.findByMobileNumber(customer.mobileNumber)).thenReturn(Optional.of(customer))
        `when`(accountsRepository.findByCustomerId(customer.customerId)).thenReturn(Optional.of(accounts))

        // Execução do teste
        val result = accountsService.fetchAccount(customer.mobileNumber)

        // Verificações
        assertNotNull(result)
        assertEquals(customer.name, result.name)
        assertEquals(accounts.accountNumber, result.accounts?.accountNumber)
        verify(customerRepository, times(1)).findByMobileNumber(customer.mobileNumber)
        verify(accountsRepository, times(1)).findByCustomerId(customer.customerId)
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar conta com número de telefone inexistente")
    fun testFetchAccount_ResourceNotFound() {
        // Configuração do mock
        `when`(customerRepository.findByMobileNumber(customer.mobileNumber)).thenReturn(Optional.empty())

        // Execução e verificação da exceção
        val exception = assertThrows(ResourceNotFoundException::class.java) {
            accountsService.fetchAccount(customer.mobileNumber)
        }

        assertEquals(
            "Customer not found with the given input data mobileNumber : '${customer.mobileNumber}'",
            exception.message
        )
    }

    @Test
    @DisplayName("Deve atualizar uma conta com sucesso")
    fun testUpdateAccount() {
        // Configuração do mock
        customerDTO.accounts = AccountsMapper.mapToAccountsDTO(accounts)
        `when`(accountsRepository.findById(accounts.accountNumber)).thenReturn(Optional.of(accounts))
        `when`(customerRepository.findById(customer.customerId)).thenReturn(Optional.of(customer))
        `when`(accountsRepository.save(any(Accounts::class.java))).thenReturn(accounts)
        `when`(customerRepository.save(any(Customer::class.java))).thenReturn(customer)

        // Execução do teste
        val result = accountsService.updateAccount(customerDTO)

        // Verificações
        assertTrue(result)
        verify(accountsRepository, times(1)).findById(accounts.accountNumber)
        verify(customerRepository, times(1)).findById(customer.customerId)
        verify(accountsRepository, times(1)).save(any(Accounts::class.java))
        verify(customerRepository, times(1)).save(any(Customer::class.java))
    }

    @Test
    @DisplayName("Deve deletar uma conta com sucesso")
    fun testDeleteAccount() {
        // Configuração do mock
        `when`(customerRepository.findByMobileNumber(customer.mobileNumber)).thenReturn(Optional.of(customer))

        // Execução do teste
        val result = accountsService.deleteAccount(customer.mobileNumber)

        // Verificações
        assertTrue(result)
        verify(customerRepository, times(1)).findByMobileNumber(customer.mobileNumber)
        verify(accountsRepository, times(1)).deleteByCustomerId(customer.customerId)
        verify(customerRepository, times(1)).deleteById(customer.customerId)
    }
}