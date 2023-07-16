package com.fiveonetwo.particle.shared.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
        private val tokenProvider: TokenProvider,
) : OncePerRequestFilter() {
    private val BEARER = "Bearer "
    private val AUTHENTICATION = "Authorization"

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        request.getHeader(AUTHENTICATION)?.let { header ->
            if (header.startsWith(BEARER)) {
                val token = header.substring(BEARER.length)
                if (tokenProvider.validateToken(token)) {
                    val authentication = tokenProvider.getAuthentication(token)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}