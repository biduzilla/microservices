package com.example.gatewayserver.filters

import com.example.gatewayserver.filters.FilterUtility.Companion.CORRELATION_ID
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import reactor.core.publisher.Mono

@Configuration
class ResponseTraceFilter {
    private val logger = LoggerFactory.getLogger(ResponseTraceFilter::class.java)

    @Autowired
    private lateinit var filterUtility: FilterUtility

    @Bean
    fun postGlobalFilter(): GlobalFilter {
        return GlobalFilter { exchange, chain ->
            chain.filter(exchange).then(Mono.fromRunnable {
                val requestHeaders: HttpHeaders = exchange.request.headers
                val correlationId = filterUtility.getCorrelationId(requestHeaders)

                if(exchange.response.headers.containsKey(CORRELATION_ID)){
                    logger.debug("Updated the correlation id to the outbound headers: {}", correlationId)
                    correlationId?.let {
                        exchange.response.headers.add(CORRELATION_ID, it)
                    }
                }

            })
        }
    }
}