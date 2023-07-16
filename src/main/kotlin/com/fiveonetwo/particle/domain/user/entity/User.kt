package com.fiveonetwo.particle.domain.user.entity

import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "users")
@Entity
class User(
        @Id
        @Column(name = "user_id")
        val id: String = uuid(),
        @Column(name = "nickname", columnDefinition = "text")
        val nickname: String = "티클",
        @Column(name = "provider")
        val provider: String,
        @Column(name = "identifier", columnDefinition = "text")
        val identifier: String,
)