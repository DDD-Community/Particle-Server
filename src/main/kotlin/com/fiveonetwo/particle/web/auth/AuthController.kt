package com.fiveonetwo.particle.web.auth

import com.fiveonetwo.particle.application.auth.AuthCommandApplication
import com.fiveonetwo.particle.domain.auth.dto.LoginRequest
import com.fiveonetwo.particle.domain.auth.dto.LoginSuccessResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authCommandApplication: AuthCommandApplication
) {
    @PostMapping("/login")
    fun login(
        @RequestBody login: LoginRequest
    ): LoginSuccessResponse = authCommandApplication.login(
        provider = login.provider,
        identifier = login.identifier
    )
}