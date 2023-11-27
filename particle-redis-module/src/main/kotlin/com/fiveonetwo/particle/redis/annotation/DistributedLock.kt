package com.fiveonetwo.particle.redis.annotation

import java.util.concurrent.TimeUnit

@Repeatable
annotation class DistributedLock(
    val key: String,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
    val waitTime: Long = 5L,
    val expireTime: Long = 3L,
)
