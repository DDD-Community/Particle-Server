package com.fiveonetwo.particle.domain.user.dto

import com.fiveonetwo.particle.domain.user.entity.User

class UserCreateDTO(
        val provider: String,
        val identifier: String,
) {
    fun toEntity(): User = User(provider = provider, identifier = identifier)
}