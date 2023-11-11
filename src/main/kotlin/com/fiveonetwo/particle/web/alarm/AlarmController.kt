package com.fiveonetwo.particle.web.alarm

import com.fiveonetwo.particle.application.alarm.AlarmCommandApplication
import com.fiveonetwo.particle.application.alarm.AlarmQueryApplication
import com.fiveonetwo.particle.web.alarm.dto.AlarmCreateRequest
import com.fiveonetwo.particle.web.alarm.dto.AlarmReadResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Tag(name = "알림", description = "사용자 알림 생성, 삭제, 조회")
@RestController
@RequestMapping("/api/v1/alarm")
class AlarmController(
    private val alarmCommandApplication: AlarmCommandApplication,
    private val alarmQueryApplication: AlarmQueryApplication,
) {
    @PostMapping("")
    fun create(
        principal: Principal,
        @RequestBody request: AlarmCreateRequest,
    ): AlarmReadResponse = alarmCommandApplication.create(principal.name, request)

    @GetMapping("/my")
    fun findMyAlarms(
        principal: Principal,
    ): List<AlarmReadResponse> = alarmQueryApplication.findMyAlarms(principal.name)

    @PostMapping("/send")
    fun send(@RequestParam token: String) = alarmCommandApplication.send(token)

    @DeleteMapping("/{alarmId}")
    fun delete(
        principal: Principal,
        @PathVariable alarmId: String
    ) = alarmCommandApplication.delete(principal.name, alarmId)
}