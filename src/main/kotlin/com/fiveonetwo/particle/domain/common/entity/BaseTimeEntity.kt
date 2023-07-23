package com.fiveonetwo.particle.domain.common.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
open class BaseTimeEntity(
        @CreatedDate
        @Column(name = "created_at", nullable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),
        @LastModifiedDate
        @Column(name = "updated_at", nullable = false)
        var updatedAt: LocalDateTime = LocalDateTime.now(),
)