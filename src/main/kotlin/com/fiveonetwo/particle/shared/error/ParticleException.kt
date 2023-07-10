package com.fiveonetwo.particle.shared.error

open class ParticleException(val errorCode: ErrorCode) : RuntimeException()

class ErrorCode(
        val status: Int,
        val message: String,
)