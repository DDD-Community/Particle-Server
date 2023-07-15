package com.fiveonetwo.particle.infra.user.adapter

import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.infra.user.error.UserNotFoundException
import com.fiveonetwo.particle.infra.user.repository.UserRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class UserAdapter(
        private val r2dbcUserRepository: UserRepository
) {
    suspend fun isExistUser(provider: String, identifier: String): Boolean = r2dbcUserRepository.existsByProviderAndIdentifier(provider, identifier)
    suspend fun save(user: User): User = r2dbcUserRepository.save(user).awaitSingle()
    suspend fun findUserByProviderAndIdentifier(provider: String, identifier: String): User = r2dbcUserRepository.findUserByProviderAndAndIdentifier(provider, identifier)
            ?: throw UserNotFoundException()
}