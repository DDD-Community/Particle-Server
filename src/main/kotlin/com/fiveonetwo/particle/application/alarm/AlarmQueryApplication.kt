package com.fiveonetwo.particle.application.alarm

import com.fiveonetwo.particle.domain.alarm.service.AlarmService
import com.fiveonetwo.particle.domain.user.service.UserService
import com.fiveonetwo.particle.web.alarm.dto.AlarmReadResponse
import org.springframework.stereotype.Component

@Component
class AlarmQueryApplication(
    private val alarmService: AlarmService,
    private val userService: UserService,
) {
    fun findMyAlarms(loginId: String): List<AlarmReadResponse> =
        alarmService.findMyAlarms(user = userService.mustFindById(loginId))
            .map { alarm ->
                AlarmReadResponse(
                    id = alarm.id,
                    name = alarm.name,
                    time = alarm.time,
                    isUse = alarm.isUse
                )
            }
}