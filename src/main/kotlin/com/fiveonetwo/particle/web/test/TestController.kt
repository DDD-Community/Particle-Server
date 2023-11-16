package com.fiveonetwo.particle.web.test

import com.fiveonetwo.particle.domain.alarm.service.AlarmService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "테스트")
@RestController
@RequestMapping("/test")
class TestController(
    private val alarmService: AlarmService,
) {
    @PostMapping("/alarm/send")
    fun sendTestAlarm(
        @RequestParam title: String,
        @RequestParam body: String,
        @RequestParam topic: String
    ) = alarmService.send(title, body, topic)

    @PostMapping("/v1/alarm/send/using/token")
    fun sendTestAlarmUsingToken(
        @RequestParam title: String,
        @RequestParam body: String,
        @RequestParam token: String,
    ) = alarmService.sendUsingToken(title, body, token, mutableMapOf())
}