package com.example.accounts.controller

import com.example.accounts.constants.AccountsConstants
import com.example.accounts.dto.CustomerDTO
import com.example.accounts.dto.ResponseDTO
import com.example.accounts.service.IAccountsService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(AccountsController::class)
class AccountsControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockitoBean
    private lateinit var iAccountsService: IAccountsService

    private val objectMapper = ObjectMapper()

    @Test
    fun `should create account and return 201`() {
        val customerDTO = CustomerDTO(name = "John Doe", mobileNumber = "1234567890")

        Mockito.doNothing().`when`(iAccountsService).createAccount(customerDTO)

        mockMvc.perform(
            post("/api/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_201))
            .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_201))
    }

    @Test
    fun `should fetch account details and return 200`() {
        val customerDTO = CustomerDTO(name = "John Doe", mobileNumber = "1234567890")

        Mockito.`when`(iAccountsService.fetchAccount("1234567890")).thenReturn(customerDTO)

        mockMvc.perform(get("/api/fetch")
            .param("mobileNumber", "1234567890")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.mobileNumber").value("1234567890"))
    }

    @Test
    fun `should update account and return 200`() {
        val customerDTO = CustomerDTO(name = "Jane Doe", mobileNumber = "0987654321")

        Mockito.`when`(iAccountsService.updateAccount(customerDTO)).thenReturn(true)

        mockMvc.perform(
            put("/api/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_200))
            .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_200))
    }

    @Test
    fun `should return 417 when update fails`() {
        val customerDTO = CustomerDTO(name = "Jane Doe", mobileNumber = "0987654321")

        Mockito.`when`(iAccountsService.updateAccount(customerDTO)).thenReturn(false)

        mockMvc.perform(
            put("/api/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDTO))
        ).andExpect(status().isExpectationFailed)
            .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_417))
            .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_417_UPDATE))
    }

    @Test
    fun `should delete account and return 200`() {
        Mockito.`when`(iAccountsService.deleteAccount("1234567890")).thenReturn(true)

        mockMvc.perform(delete("/api/delete")
            .param("mobileNumber", "1234567890")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_200))
            .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_200))
    }

    @Test
    fun `should return 417 when delete fails`() {
        Mockito.`when`(iAccountsService.deleteAccount("1234567890")).thenReturn(false)

        mockMvc.perform(delete("/api/delete")
            .param("mobileNumber", "1234567890")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isExpectationFailed)
            .andExpect(jsonPath("$.statusCode").value(AccountsConstants.STATUS_417))
            .andExpect(jsonPath("$.statusMsg").value(AccountsConstants.MESSAGE_417_DELETE))
    }
}
