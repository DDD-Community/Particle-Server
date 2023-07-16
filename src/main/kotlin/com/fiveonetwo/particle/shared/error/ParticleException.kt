package com.fiveonetwo.particle.shared.error

open class ParticleException(
        code: String,
        override val message: String,
        status: Int,
) : RuntimeException() {
    val errorResponse: ErrorResponse = ErrorResponse(message, status, code)
}

