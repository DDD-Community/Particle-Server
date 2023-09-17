package com.fiveonetwo.particle.web.alarm.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalTime

class AlarmCreateRequest(
    val name: String,
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Schema(type = "String", pattern = "HH:mm:ss")
    val time: LocalTime,
    val isUse: Boolean,
)