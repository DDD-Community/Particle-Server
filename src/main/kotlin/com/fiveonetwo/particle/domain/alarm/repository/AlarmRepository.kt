package com.fiveonetwo.particle.domain.alarm.repository

import com.fiveonetwo.particle.domain.alarm.entity.Alarm
import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlarmRepository : JpaRepository<Alarm, String> {
    fun findAllByUser(user: User): List<Alarm>
}