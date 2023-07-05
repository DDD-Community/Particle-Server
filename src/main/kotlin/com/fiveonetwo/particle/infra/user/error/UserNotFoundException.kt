package com.fiveonetwo.particle.infra.user.error

import com.fiveonetwo.particle.shared.error.ErrorCode
import com.fiveonetwo.particle.shared.error.ParticleException

class UserNotFoundException : ParticleException(ErrorCode(400, "user not found"))