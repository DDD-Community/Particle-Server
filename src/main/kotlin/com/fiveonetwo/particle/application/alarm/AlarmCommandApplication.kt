package com.fiveonetwo.particle.application.alarm

import com.fiveonetwo.particle.domain.alarm.entity.Alarm
import com.fiveonetwo.particle.domain.alarm.service.AlarmService
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.web.alarm.dto.AlarmCreateRequest
import com.fiveonetwo.particle.web.alarm.dto.AlarmReadResponse
import org.springframework.stereotype.Component

@Component
class AlarmCommandApplication(
    private val alarmService: AlarmService,
    private val userService: UserService,
) {

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
}