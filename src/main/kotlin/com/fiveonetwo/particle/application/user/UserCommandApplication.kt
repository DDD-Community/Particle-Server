package com.fiveonetwo.particle.application.user

import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.tag.error.TagConvertException
import com.fiveonetwo.particle.domain.user.dto.UserReadDTO
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.web.user.dto.UserReadResponse
import org.springframework.stereotype.Component

@Component
class UserCommandApplication(
    private val userService: UserService
) {
    fun updateMyInterestedTags(loginId: String, interestedTags: List<String>): UserReadResponse {
        val loginUser = userService.mustFindById(loginId)
        userService.updateInterestedTags(loginUser, convert(interestedTags))
        return loginUser
            .let { UserReadDTO.from(loginUser) }
            .let { dto ->
                UserReadResponse(
                    id = dto.id,
                    nickname = dto.nickname,
                    profileImageUrl = dto.profileImageUrl,
                    interestedTags = dto.interestedTags.map { tag -> tag.value }
                )
            }
    }

    private fun convert(tagValues: List<String>): List<Tag> {
        return try {
            tagValues.map { tag -> Tag.originalValueOf(tag) }
        } catch (ex: Exception) {
            throw TagConvertException()
        }
    }
}