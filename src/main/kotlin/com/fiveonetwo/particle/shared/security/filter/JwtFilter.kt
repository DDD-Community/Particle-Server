package com.fiveonetwo.particle.shared.security.filter

import com.fiveonetwo.particle.shared.security.provider.TokenProvider
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtFilter(
        private val tokenProvider: TokenProvider
) : WebFilter {
    val BEARER = "Bearer "
    val AUTHORIZATION = "Authorization"

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authorization = exchange.request.headers[AUTHORIZATION]?.first()
        if (authorization != null && authorization.startsWith(BEARER)) {
            val bearer = authorization.substring(BEARER.length)
            if (tokenProvider.validateToken(bearer)) {
                val authentication = tokenProvider.getAuthentication(bearer)
                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
            }
        }
        return chain.filter(exchange)
    }
}