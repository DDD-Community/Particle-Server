package com.fiveonetwo.particle.infra.user.repository

import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : R2dbcRepository<User, String> {
    suspend fun existsByProviderAndIdentifier(provider: String, identifier: String): Boolean
    suspend fun findUserByProviderAndAndIdentifier(provider: String, identifier: String): User?
}