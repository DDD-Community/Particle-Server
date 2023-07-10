package com.fiveonetwo.particle.domain.user.service

import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.infra.user.adapter.UserAdapter
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userAdapter: UserAdapter
) {
    suspend fun isExistUser(provider: String, identifier: String): Boolean = userAdapter.isExistUser(provider, identifier)
    suspend fun createUser(create: UserCreateDTO): UserReadDTO = userAdapter.save(create.toEntity()).let { user -> UserReadDTO.from(user) }
    suspend fun findUserByProviderAndIdentifier(provider: String, identifier: String): UserReadDTO = userAdapter.findUserByProviderAndIdentifier(provider, identifier).let { user -> UserReadDTO.from(user) }
}