package com.example.accounts.controller

import com.example.accounts.constants.AccountsConstants
import com.example.accounts.dto.AccountsContactInfoDto
import com.example.accounts.dto.CustomerDTO
import com.example.accounts.dto.ErrorResponseDTO
import com.example.accounts.dto.ResponseDTO
import com.example.accounts.service.IAccountsService
import io.github.resilience4j.retry.annotation.Retry
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Tag(
    name = "CRUD REST APIs for Accounts in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path = ["/api"], produces = [MediaType.APPLICATION_JSON_VALUE])
@Validated
class AccountsController(
    private val iAccountsService: IAccountsService,
    private val env: Environment,
    private val accountsContactInfoDto: AccountsContactInfoDto
) {

//    @Value("\${build.version}")
//    private lateinit var buildVersion: String

    @Operation(
        summary = "Create Account REST API",
        description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "HTTP Status CREATED"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @PostMapping(
        "/create",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createAccount(@Valid @RequestBody data: CustomerDTO): ResponseEntity<ResponseDTO> {
        iAccountsService.createAccount(data)
        return ResponseEntity
            .status(
                HttpStatus.CREATED
            ).body(
                ResponseDTO(
                    statusCode = AccountsConstants.STATUS_201,
                    statusMsg = AccountsConstants.MESSAGE_201
                )
            )
    }

    @Operation(
        summary = "Fetch Account Details REST API",
        description = "REST API to fetch Customer &  Account details based on a mobile number"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )

    @GetMapping("/fetch")
    fun fetchAccountDetails(
        @RequestParam
        @Pattern(
            regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits"
        ) mobileNumber: String
    ): ResponseEntity<CustomerDTO> {
        val data = iAccountsService.fetchAccount(mobileNumber = mobileNumber)
        return ResponseEntity.status(HttpStatus.OK).body(data)
    }

    @Operation(
        summary = "Update Account Details REST API",
        description = "REST API to update Customer &  Account details based on a account number"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            ApiResponse(
                responseCode = "417",
                description = "Expectation Failed"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @PutMapping("/update")
    fun updateAccountDetails(@Valid @RequestBody customerDto: CustomerDTO): ResponseEntity<ResponseDTO> {
        val isUpdated = iAccountsService.updateAccount(customerDto)
        return if (isUpdated) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body<ResponseDTO>(ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200))
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body<ResponseDTO>(ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE))
        }
    }

    @Operation(
        summary = "Delete Account & Customer Details REST API",
        description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "HTTP Status OK"
            ),
            ApiResponse(
                responseCode = "417",
                description = "Expectation Failed"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @DeleteMapping("/delete")
    fun deleteAccountDetails(
        @RequestParam @Pattern(
            regexp = "(^$|[0-9]{10})",
            message = "Mobile number must be 10 digits"
        ) mobileNumber: String?
    ): ResponseEntity<ResponseDTO> {
        val isDeleted = iAccountsService.deleteAccount(mobileNumber!!)
        return if (isDeleted) {
            ResponseEntity
                .status(HttpStatus.OK)
                .body<ResponseDTO>(ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200))
        } else {
            ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body<ResponseDTO>(ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE))
        }
    }

//    @Operation(
//        summary = "Get build version",
//        description = "Get build information that is deployed into accounts microservices"
//    )
//    @ApiResponses(
//        value = [
//            ApiResponse(
//                responseCode = "201",
//                description = "HTTP Status CREATED"
//            ),
//            ApiResponse(
//                responseCode = "500",
//                description = "HTTP Status Internal Server Error",
//                content = [
//                    Content(
//                        schema = Schema(implementation = ErrorResponseDTO::class)
//                    )
//                ]
//            )
//        ]
//    )
//    @GetMapping("/build-info")
//     fun getBuildInfo():ResponseEntity<String>{
//        return ResponseEntity.status(HttpStatus.OK).body(buildVersion)
//    }

    @Operation(
        summary = "Get Contact Info",
        description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "HTTP Status CREATED"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @Retry(name = "getContactInfo", fallbackMethod = "getContactInfoFallBack")
    @GetMapping("/contact-info")
    fun getContactInfo(): ResponseEntity<AccountsContactInfoDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(accountsContactInfoDto);
    }

    fun getContactInfoFallBack(throwable: Throwable): ResponseEntity<AccountsContactInfoDto> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(null);
    }


    @Operation(
        summary = "Get Java version",
        description = "Get Java versions details that is installed into accounts microservice"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "HTTP Status CREATED"
            ),
            ApiResponse(
                responseCode = "500",
                description = "HTTP Status Internal Server Error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/java-version")
    fun getJavaVersion(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body(env.getProperty("JAVA_HOME"))
    }
}