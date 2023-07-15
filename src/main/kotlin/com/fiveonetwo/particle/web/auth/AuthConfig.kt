package com.fiveonetwo.particle.web.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthConfig(
        private val authHandler: AuthHandler
) {
    @Bean
    fun authRouter() = coRouter {
        "/auth".nest {
            POST("/login") { request -> authHandler.login(request) }
        }
    }
}