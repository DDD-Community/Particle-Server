package com.fiveonetwo.particle.web.auth

import com.fiveonetwo.particle.application.auth.AuthCommandApplication
import com.fiveonetwo.particle.domain.auth.dto.LoginRequest
import com.fiveonetwo.particle.domain.auth.dto.LoginSuccessResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "인증/인가", description = "사용자 로그인/로그아웃/회원탈퇴 기능")
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

    @DeleteMapping("/withdrawal")
    fun withdrawal(principal: Principal) {
        authCommandApplication.withdrawal(loginId = principal.name)
    }
}