package com.fiveonetwo.particle.web.user

import com.fiveonetwo.particle.application.user.UserCommandApplication
import com.fiveonetwo.particle.application.user.UserQueryApplication
import com.fiveonetwo.particle.web.user.dto.UserReadResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "유저", description = "사용자 프로필 조회/관심 태그 변경 기능")
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
        interestedTags = interestedTags
    )

    @GetMapping("/my/profile")
    fun getMyProfile(
        principal: Principal,
    ): UserReadResponse = userQueryApplication.getMyProfile(principal.name)
}