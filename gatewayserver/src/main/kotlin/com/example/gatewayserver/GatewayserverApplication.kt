package com.example.gatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import java.time.LocalDateTime

@SpringBootApplication
class GatewayserverApplication {
    @Bean
    fun eazyBankRouteConfig(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator {
        return routeLocatorBuilder.routes()
            .route { p ->
                p.path("/eazybank/accounts/**")
                    .filters { f ->
                        f.rewritePath("/eazybank/accounts/(?<segment>.*)", "/${'$'}{segment}")
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                            .circuitBreaker{config->
                                config.setName("accountsCircuitBreaker")
                            }
                    }
                    .uri("lb://ACCOUNTS")
            }
            .route { p ->
                p.path("/eazybank/loans/**")
                    .filters { f ->
                        f.rewritePath("/eazybank/loans/(?<segment>.*)", "/${'$'}{segment}")
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    }
                    .uri("lb://LOANS")
            }
            .route { p ->
                p.path("/eazybank/cards/**")
                    .filters { f ->
                        f.rewritePath("/eazybank/cards/(?<segment>.*)", "/${'$'}{segment}")
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                    }
                    .uri("lb://CARDS")
            }
            .build()
    }
}

fun main(args: Array<String>) {
    runApplication<GatewayserverApplication>(*args)
}




