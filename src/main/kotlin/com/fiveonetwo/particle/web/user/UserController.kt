package com.fiveonetwo.particle.web.user

import com.fiveonetwo.particle.application.user.UserCommandApplication
import com.fiveonetwo.particle.application.user.UserQueryApplication
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.tag.error.TagConvertException
import com.fiveonetwo.particle.web.user.dto.UserReadResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userCommandApplication: UserCommandApplication,
    private val userQueryApplication: UserQueryApplication,
) {
    @PutMapping("/interested/tags")
    fun updateInterestedTags(
        principal: Principal,
        @RequestParam
        interestedTags: List<String>
    ): UserReadResponse = userCommandApplication.updateMyInterestedTags(
        loginId = principal.name,
        interestedTags = convert(interestedTags)
    )

    private fun convert(tagValues: List<String>): List<Tag> {
        return try {
            tagValues.map { tag -> Tag.originalValueOf(tag) }
        } catch (ex: Exception) {
            throw TagConvertException()
        }
    }

    @GetMapping("/my/profile")
    fun getMyProfile(
        principal: Principal,
    ): UserReadResponse = userQueryApplication.getMyProfile(principal.name)
}