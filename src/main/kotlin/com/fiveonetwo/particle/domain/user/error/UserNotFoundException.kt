package com.fiveonetwo.particle.domain.user.error

import com.fiveonetwo.particle.shared.error.ParticleException

class UserNotFoundException : ParticleException(
        message = "user not found",
        status = 400,
        code = "USER_NOT_FOUND"
)