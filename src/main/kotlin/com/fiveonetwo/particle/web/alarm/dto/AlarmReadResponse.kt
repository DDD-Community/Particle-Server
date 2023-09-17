package com.fiveonetwo.particle.web.alarm.dto

import java.time.LocalTime

class AlarmReadResponse(
    val id: String,
    val name: String,
    val time: LocalTime,
    val isUse: Boolean,
)