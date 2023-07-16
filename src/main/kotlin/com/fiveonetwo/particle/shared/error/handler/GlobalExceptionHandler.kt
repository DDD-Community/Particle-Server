package com.fiveonetwo.particle.shared.error.handler

import com.fiveonetwo.particle.shared.error.ErrorResponse
import com.fiveonetwo.particle.shared.error.ParticleException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ParticleException::class)
    fun particleExceptionHandler(e: ParticleException): ResponseEntity<ErrorResponse> =
            ErrorResponse(e.message, e.errorResponse.status, e.errorResponse.code)
                    .let { ResponseEntity.status(e.errorResponse.status).body(it) }

    @ExceptionHandler(IllegalAccessException::class)
    fun illegalAccessException(e: IllegalAccessException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(403).body(ErrorResponse("권한이 없습니다.", 403, "ILLEGAL_ACCESS"))
    }
}