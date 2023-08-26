package com.fiveonetwo.particle.domain.user.service

import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.user.dto.UserCreateDTO
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.domain.user.error.UserNotFoundException
import com.fiveonetwo.particle.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.String.format
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun createUser(create: UserCreateDTO) = userRepository.save(create.toUser(generatedUserId(create.provider)))
        .let { UserReadDTO.from(it) }

    fun existsById(id: String) = userRepository.existsById(id)

    fun existsByProviderAndIdentifier(provider: String, identifier: String) = userRepository.existsByProviderAndIdentifier(provider, identifier)

    fun findByProviderAndIdentifier(provider: String, identifier: String) = userRepository.mustFindByProviderAndIdentifier(provider, identifier)
        .let { UserReadDTO.from(it) }

    fun mustFindById(userId: String): User = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()

    fun generatedUserId(provider: String): String {
        fun nextId() = "$provider${format("%06d", Random.nextInt(1000000))}"
        var newUserId = nextId()
        while (userRepository.existsById(newUserId)) newUserId = nextId()
        return newUserId
    }

    @Transactional
    fun updateInterestedTags(user: User, interestedTags: List<Tag>) {
        user.interestedTags.clear()
        user.interestedTags.addAll(interestedTags)
    }
}