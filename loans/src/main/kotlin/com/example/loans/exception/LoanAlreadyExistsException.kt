package com.example.loans.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class LoanAlreadyExistsException(message: String) : RuntimeException(message)