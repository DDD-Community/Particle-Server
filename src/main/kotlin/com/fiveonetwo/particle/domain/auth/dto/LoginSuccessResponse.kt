package com.fiveonetwo.particle.domain.auth.dto

class LoginSuccessResponse(
    val tokens: LoginToken,
    val isNew: Boolean,
)