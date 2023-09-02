package com.fiveonetwo.particle.web.user.dto

import com.fiveonetwo.particle.domain.record.entity.Tag


class UserReadResponse(
    val id: String,
    val nickname: String,
    val profileImageUrl: String,
    val interestedTags: List<String>
)