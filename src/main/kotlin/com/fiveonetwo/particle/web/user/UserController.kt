package com.fiveonetwo.particle.web.user

import com.fiveonetwo.particle.application.user.UserCommandApplication
import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userCommandApplication: UserCommandApplication
) {
    @PutMapping("/interested/tags")
    fun updateInterestedTags(
        principal: Principal,
        @RequestParam
        interestedTags: List<RecordTagValue>
    ): UserReadDTO = userCommandApplication.updateMyInterestedTags(
        loginId = principal.name,
        interestedTags = interestedTags
    )
}