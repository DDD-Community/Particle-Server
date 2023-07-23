package com.fiveonetwo.particle.domain.user.entity

import com.fiveonetwo.particle.domain.common.entity.BaseTimeEntity
import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "users")
@Entity
class User(
        @Id
        @Column(name = "user_id", nullable = false)
        val id: String = uuid(),
        @Column(name = "nickname", nullable = false, columnDefinition = "text")
        val nickname: String = "티클",
        @Column(name = "provider", nullable = false)
        val provider: String,
        @Column(name = "identifier", nullable = false, columnDefinition = "text")
        val identifier: String,
) : BaseTimeEntity()