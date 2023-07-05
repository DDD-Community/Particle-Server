package com.fiveonetwo.particle.domain.auth.service

import com.fiveonetwo.particle.domain.auth.dto.LoginDTO
import com.fiveonetwo.particle.domain.auth.dto.TokenDTO
import com.fiveonetwo.particle.shared.security.provider.TokenProvider
import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
class AuthService(
        private val userService: UserService,
        private val tokenProvider: TokenProvider,
        private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>
) {

    suspend fun login(login: LoginDTO): TokenDTO {
        val read = if (userService.isExistUser(login.provider, login.identifier)) {
            userService.findUserByProviderAndIdentifier(login.provider, login.identifier)
        } else {
            userService.createUser(UserCreateDTO(login.provider, login.identifier))
        }

        val tokens = TokenDTO(
                accessToken = tokenProvider.createAccessToken(subject = read.id),
                refreshToken = tokenProvider.createRefreshToken(subject = read.id)
        )

        reactiveRedisTemplate.opsForValue().also { ops ->
            ops.set(read.id, tokens.accessToken, Duration.of(tokenProvider.refreshDuration, ChronoUnit.MILLIS)).awaitSingle()
        }
        return tokens
    }
}