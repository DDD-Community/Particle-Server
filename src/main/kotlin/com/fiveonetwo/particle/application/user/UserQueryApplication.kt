package com.fiveonetwo.particle.application.user

import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import org.springframework.stereotype.Component

@Component
class UserQueryApplication(
    private val userService: UserService,
) {
    fun getMyProfile(loginId: String): UserReadDTO = userService.mustFindById(userId = loginId).let { user -> UserReadDTO.from(user) }
}