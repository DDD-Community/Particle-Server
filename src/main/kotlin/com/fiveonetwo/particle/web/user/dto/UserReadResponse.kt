package com.fiveonetwo.particle.web.user.dto


class UserReadResponse(
    val id: String,
    val nickname: String,
    val profileImageUrl: String,
    val interestedTags: List<String>
)