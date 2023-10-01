package com.fiveonetwo.particle.application.alarm

import com.fiveonetwo.particle.domain.alarm.entity.Alarm
import com.fiveonetwo.particle.domain.alarm.service.AlarmService
import com.fiveonetwo.particle.domain.alarm.service.AlarmTopic.PARTICLE_REMINDER_ALARM
import com.fiveonetwo.particle.domain.record.error.RecordNotFoundException
import com.fiveonetwo.particle.domain.record.service.RecordService
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.shared.utils.logger
import com.fiveonetwo.particle.web.alarm.dto.AlarmCreateRequest
import com.fiveonetwo.particle.web.alarm.dto.AlarmReadResponse
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class AlarmCommandApplication(
    private val alarmService: AlarmService,
    private val userService: UserService,
    private val recordService: RecordService,
) {
    private val log: Logger = logger<AlarmCommandApplication>()

    fun create(loginId: String, request: AlarmCreateRequest): AlarmReadResponse {
        val user = userService.mustFindById(loginId)
        val alarm = Alarm(
            user = user,
            name = request.name,
            time = request.time,
            isUse = request.isUse,
        )

        return alarmService.save(alarm).let { savedAlarm ->
            AlarmReadResponse(
                id = savedAlarm.id,
                name = savedAlarm.name,
                time = savedAlarm.time,
                isUse = savedAlarm.isUse
            )
        }
    }

    fun send() {
        val record = recordService.findRandomRecord() ?: throw RecordNotFoundException()
        val mainItems = record.mainItems()

        if (mainItems.isEmpty()) {
            log.info("알림에 사용할 record item을 찾지 못했습니다.")
            return
        }

        alarmService.send(
            title = record.title,
            body = mainItems.first().content,
            topic = PARTICLE_REMINDER_ALARM.value
        )
    }
}