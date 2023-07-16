package com.fiveonetwo.particle.domain.user.repository

import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun existsByProviderAndIdentifier(provider: String, identifier: String): Boolean
    fun findByProviderAndIdentifier(provider: String, identifier: String): User?

    @Query("SELECT u FROM User u WHERE u.provider = :provider AND u.identifier = :identifier")
    fun mustFindByProviderAndIdentifier(provider: String, identifier: String): User
}