package com.fiveonetwo.particle.web.auth

import com.fiveonetwo.particle.domain.auth.dto.LoginDTO
import com.fiveonetwo.particle.domain.auth.service.AuthService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class AuthHandler(
        private val authService: AuthService
) {

    suspend fun login(request: ServerRequest) = ServerResponse.ok().bodyValueAndAwait(authService.login(request.awaitBody(LoginDTO::class)))

}