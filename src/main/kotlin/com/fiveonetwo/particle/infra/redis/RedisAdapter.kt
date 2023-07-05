package com.fiveonetwo.particle.infra.redis

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ReactiveValueOperations
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisAdapter(
        private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>

) {
    suspend fun set(key: String, value: String, timeout: Duration) = reactiveRedisTemplate.opsForValue().also { ops ->
        ops.set(key, value, timeout).awaitSingle()
    }
}