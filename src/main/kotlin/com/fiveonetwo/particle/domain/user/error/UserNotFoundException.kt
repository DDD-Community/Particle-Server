package com.fiveonetwo.particle.domain.user.error

import com.fiveonetwo.particle.shared.error.ParticleException

class UserNotFoundException : ParticleException(
        message = "user not found",
        status = 404,
        code = "USER_NOT_FOUND"
)