package com.fiveonetwo.particle.domain.alarm.entity

import com.fiveonetwo.particle.domain.user.entity.User
import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalTime

@Entity
@Table(name = "alarm_config")
class AlarmConfig(
    @Id
    @Column(name = "alarm_config_id")
    val id: String = uuid(),
    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
    @Column(name = "name")
    val name: String,
    @Column(name = "time")
    val time: LocalTime,
    @Column(name = "is_use")
    val isUse: Boolean = true
) {
}