package com.fiveonetwo.particle.domain.tag.error

import com.fiveonetwo.particle.shared.error.ParticleException

class TagConvertException : ParticleException(
    code = "TAG_CONVERT_ERROR",
    message = "tag value is not support",
    status = 400
) {
}