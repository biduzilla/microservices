package com.example.gatewayserver.filters

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*

@Component
@Order(1)
class RequestTraceFilter(
    private val filterUtility: FilterUtility
) : GlobalFilter {
    private val logger = LoggerFactory.getLogger(RequestTraceFilter::class.java)
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val requestHeaders = exchange.request.headers

        val exchangeWithId = if (isCorrelationIdPresent(requestHeaders)) {
            logger.debug(
                "eazyBank-correlation-id found in RequestTraceFilter: {}",
                filterUtility.getCorrelationId(requestHeaders)
            )
            exchange
        } else {
            val correlationId = generateCorrelationId()
            logger.debug("eazyBank-correlation-id generated in RequestTraceFilter: {}", correlationId)
            filterUtility.setCorrelationId(exchange, correlationId)
        }

        return chain.filter(exchangeWithId)
    }

    private fun isCorrelationIdPresent(requestHeaders: HttpHeaders): Boolean {
        return filterUtility.getCorrelationId(requestHeaders) != null
    }

    private fun generateCorrelationId(): String {
        return UUID.randomUUID().toString()
    }


}