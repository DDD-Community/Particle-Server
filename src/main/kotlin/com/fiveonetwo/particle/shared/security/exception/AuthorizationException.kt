package com.fiveonetwo.particle.shared.security.exception

import com.fiveonetwo.particle.shared.error.ParticleException

class AuthorizationException : ParticleException(
    code = "NO_AUTHORIZATION",
    message = "no authorization please check your authentication",
    status = 403
)