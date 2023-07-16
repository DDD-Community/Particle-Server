package com.fiveonetwo.particle.domain.user.service

import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
        private val userRepository: UserRepository
) {
    fun createUser(create: UserCreateDTO) = userRepository.save(create.toUser())
            .let { UserReadDTO.from(it) }

    @Transactional(readOnly = true)
    fun existsById(id: String) = userRepository.existsById(id)

    @Transactional(readOnly = true)
    fun existsByProviderAndIdentifier(provider: String, identifier: String) = userRepository.existsByProviderAndIdentifier(provider, identifier)

    fun findByProviderAndIdentifier(provider: String, identifier: String) = userRepository.mustFindByProviderAndIdentifier(provider, identifier)
            .let { UserReadDTO.from(it) }
}