package com.fiveonetwo.particle.domain.user.dto

import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
import com.fiveonetwo.particle.domain.user.entity.User

class UserReadDTO(
    val id: String,
    val nickname: String,
    val interestedTags: MutableList<RecordTagValue>
) {
    companion object {
        fun from(user: User) = UserReadDTO(
            id = user.id,
            nickname = user.nickname,
            interestedTags = user.interestedTags
        )
    }
}