package com.fiveonetwo.particle.domain.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisService(
    private val redisTemplate: StringRedisTemplate,
) {
    fun save(key: String, value: String, duration: Duration) {
        val operator = redisTemplate.opsForValue()
        operator.set(key, value, duration)
    }

    fun delete(key : String): String? = redisTemplate.opsForValue().getAndDelete(key)
}