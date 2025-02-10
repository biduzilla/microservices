package com.example.accounts.dto

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "accounts")
data class AccountsContactInfoDto(
    var message: String = "",
    var contactDetails: Map<String, String> = emptyMap(),
    var onCallSupport: List<String> = emptyList()
)
