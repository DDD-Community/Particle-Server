package com.fiveonetwo.particle.web.auth

import com.fiveonetwo.particle.domain.auth.dto.LoginDTO
import com.fiveonetwo.particle.domain.auth.dto.LoginToken
import com.fiveonetwo.particle.domain.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
        private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): LoginToken = authService.login(login)
}