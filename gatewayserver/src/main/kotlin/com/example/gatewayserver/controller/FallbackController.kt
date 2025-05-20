package com.example.gatewayserver.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class FallbackController {
    @RequestMapping("/")
    fun contactSupport() = Mono.just("An error occurred. Please try after some time or contact support team!!!")
}