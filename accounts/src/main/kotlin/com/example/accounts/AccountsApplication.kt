package com.example.accounts

import com.example.accounts.dto.AccountsContactInfoDto
import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = Info(
        title = "Accounts microservice REST API Documentation",
        description = "EazyBank Accounts microservice REST API Documentation",
        version = "v1",
        contact = Contact(
            name = "Luiz Henrique",
            email = "luiz.devs@gmail.com",
//            url = "https://www.eazybytes.com"
        ),
        license = License(
            name = "Apache 2.0",
//            url = "https://www.eazybytes.com"
        )
    ),
    externalDocs = ExternalDocumentation(
        description = "EazyBank Accounts microservice REST API Documentation",
//        url = "https://www.eazybytes.com/swagger-ui.html"
    )
)
@EnableConfigurationProperties(AccountsContactInfoDto::class)
@EnableFeignClients
class AccountsApplication

fun main(args: Array<String>) {
    runApplication<AccountsApplication>(*args)
}
