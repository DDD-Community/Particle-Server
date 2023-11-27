package com.fiveonetwo.particle.redis.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig(
    @Value(value = "\${spring.data.redis.host}")
    val host: String,
    @Value(value = "\${spring.data.redis.port}")
    val port: Int,
) {
    @Bean
    fun redisConnectionFactory(): RedissonClient {
        val config = Config()
        config.useSingleServer().address = "${REDISSON_HOST_PREFIX}$host:$port"
        return Redisson.create(config);
    }

    companion object {
        const val REDISSON_HOST_PREFIX: String = "redis://"
    }
}