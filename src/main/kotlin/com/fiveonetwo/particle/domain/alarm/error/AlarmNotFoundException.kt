package com.fiveonetwo.particle.domain.alarm.error

import com.fiveonetwo.particle.shared.error.ParticleException

class AlarmNotFoundException : ParticleException(
    code = "ALARM_NOT_FOUND",
    message = "alarm not found",
    status = 400
)