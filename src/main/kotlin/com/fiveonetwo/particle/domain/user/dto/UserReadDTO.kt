package com.fiveonetwo.particle.domain.user.dto

import com.fiveonetwo.particle.domain.user.entity.User

class UserReadDTO(
        val id: String,
        val provider: String,
) {
    companion object {
        fun from(user: User) = UserReadDTO(user.id, user.provider)
    }
}