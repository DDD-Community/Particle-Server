package com.fiveonetwo.particle.application.auth

import com.fiveonetwo.particle.application.auth.dto.LoginToken
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.redis.RedisService
import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.security.TokenProvider
import com.fiveonetwo.particle.web.auth.dto.LoginSuccessResponse
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Component
class AuthCommandApplication(
    private val userService: UserService,
    private val redisService: RedisService,
    private val tokenProvider: TokenProvider,
    private val recordService: RecordService,
) {

    @Transactional
    fun login(provider: String, identifier: String): LoginSuccessResponse {
        val isExist = userService.existsByProviderAndIdentifier(provider, identifier)
        val read: UserReadDTO = if (isExist) {
            userService.findByProviderAndIdentifier(provider, identifier)
        } else {
            userService.createUser(UserCreateDTO(provider, identifier))
        }

        val tokens = LoginToken(
            tokenProvider.createAccessToken(read.id),
            tokenProvider.createRefreshToken(read.id)
        )
        redisService.save(read.id, tokens.refreshToken, Duration.ofMillis(tokenProvider.refreshDuration))
        return LoginSuccessResponse(
            tokens = tokens,
            isNew = !isExist
        )
    }

    @Transactional
    fun logout(
        loginId: String,
    ): String? = redisService.delete(loginId)

    @Transactional
    fun withdrawal(loginId: String) {
        val user = userService.mustFindById(loginId)
        recordService.deleteAllByUser(user)
        userService.deleteUser(user)
    }
}