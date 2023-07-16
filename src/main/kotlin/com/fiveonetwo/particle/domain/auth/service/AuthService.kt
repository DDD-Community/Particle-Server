package com.fiveonetwo.particle.domain.auth.service

import com.fiveonetwo.particle.domain.auth.dto.LoginDTO
import com.fiveonetwo.particle.domain.auth.dto.LoginToken
import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.security.TokenProvider
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
@Transactional
class AuthService(
        private val userService: UserService,
        private val redisTemplate: StringRedisTemplate,
        private val tokenProvider: TokenProvider,
) {
    fun login(login: LoginDTO): LoginToken {

        val isExist = userService.existsByProviderAndIdentifier(login.provider, login.identifier)
        val read: UserReadDTO = if (isExist) {
            userService.findByProviderAndIdentifier(login.provider, login.identifier)
        } else {
            userService.createUser(UserCreateDTO(login.provider, login.identifier))
        }

        val operation = redisTemplate.opsForValue()
        val tokens = LoginToken(
                tokenProvider.createAccessToken(read.id),
                tokenProvider.createRefreshToken(read.id)
        )
        operation.set(read.id, tokens.refreshToken, tokenProvider.refreshDuration, TimeUnit.MILLISECONDS)
        return tokens
    }
}