package com.fiveonetwo.particle.domain.user.dto

import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.user.entity.User

class UserReadDTO(
    val id: String,
    val nickname: String,
    val profileImageUrl: String,
    val interestedTags: MutableList<Tag>
) {
    companion object {
        fun from(user: User) = UserReadDTO(
            id = user.id,
            nickname = user.nickname,
            profileImageUrl = user.profileImageUrl,
            interestedTags = user.interestedTags
        )
    }
}