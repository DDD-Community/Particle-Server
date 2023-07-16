package com.fiveonetwo.particle.domain.record.error

import com.fiveonetwo.particle.shared.error.ParticleException

class RecordNotFoundException : ParticleException(
        message = "record not found",
        code = "RECORD_NOT_FOUND",
        status = 400
    )