package com.fiveonetwo.particle.domain.alarm.service

import com.fiveonetwo.particle.domain.alarm.entity.Alarm
import com.fiveonetwo.particle.domain.alarm.repository.AlarmRepository
import com.fiveonetwo.particle.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlarmService(
    private val alarmRepository: AlarmRepository,
) {
    @Transactional
    fun create(entity: Alarm) {
        alarmRepository.save(entity)
    }

    @Transactional
    fun save(entity: Alarm): Alarm {
        return alarmRepository.save(entity)
    }

    @Transactional
    fun delete(entity: Alarm) {
        alarmRepository.delete(entity)
    }

    fun findMyAlarms(user: User): List<Alarm> {
        return alarmRepository.findAllByUser(user)
    }
}