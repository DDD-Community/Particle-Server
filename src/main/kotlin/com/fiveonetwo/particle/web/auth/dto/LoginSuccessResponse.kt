package com.fiveonetwo.particle.web.auth.dto

import com.fiveonetwo.particle.application.auth.dto.LoginToken

class LoginSuccessResponse(
    val tokens: LoginToken,
    val isNew: Boolean,
)