package com.fiveonetwo.particle.domain.user.dto

import com.fiveonetwo.particle.domain.user.entity.User

class UserCreateDTO(
    val provider: String,
    val identifier: String,
) {
    fun toUser(userId: String): User = User(
        id = userId,
        provider = this.provider,
        identifier = this.identifier,
    )
}