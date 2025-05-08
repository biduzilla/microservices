package com.example.gatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatewayserverApplication

fun main(args: Array<String>) {
	runApplication<GatewayserverApplication>(*args)
}
