package com.fiveonetwo.particle.application.user

import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.web.user.dto.UserReadResponse
import org.springframework.stereotype.Component

@Component
class UserQueryApplication(
    private val userService: UserService,
) {
    fun getMyProfile(loginId: String): UserReadResponse = userService.mustFindById(userId = loginId)
        .let { user -> UserReadDTO.from(user) }
        .let { dto ->
            UserReadResponse(
                id = dto.id,
                nickname = dto.nickname,
                profileImageUrl = dto.profileImageUrl,
                interestedTags = dto.interestedTags.map { tag -> tag.value }
            )
        }
}