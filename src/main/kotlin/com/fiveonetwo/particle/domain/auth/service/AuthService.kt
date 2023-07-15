package com.fiveonetwo.particle.domain.auth.service

import com.fiveonetwo.particle.domain.auth.dto.LoginDTO
import com.fiveonetwo.particle.domain.auth.dto.TokenDTO
import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.infra.redis.RedisAdapter
import com.fiveonetwo.particle.shared.security.provider.TokenProvider
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.temporal.ChronoUnit

@Service
class AuthService(
    private val userService: UserService,
    private val tokenProvider: TokenProvider,
    private val redisAdapter: RedisAdapter,
) {
    suspend fun login(login: LoginDTO): TokenDTO {
        val read = if (userService.isExistUser(login.provider, login.identifier)) {
            userService.findUserByProviderAndIdentifier(login.provider, login.identifier)
        } else {
            userService.createUser(UserCreateDTO(login.provider, login.identifier, login.nickname))
        }

        return TokenDTO(
            accessToken = tokenProvider.createAccessToken(subject = read.id),
            refreshToken = tokenProvider.createRefreshToken(subject = read.id)
        ).also { tokens ->
            redisAdapter.set(
                read.id,
                tokens.refreshToken,
                Duration.of(tokenProvider.refreshDuration, ChronoUnit.MILLIS)
            )
        }
    }
}