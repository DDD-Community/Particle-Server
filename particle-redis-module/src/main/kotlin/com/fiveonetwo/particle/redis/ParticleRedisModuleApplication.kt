package com.fiveonetwo.particle.redis

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ParticleRedisModuleApplication

fun main(args: Array<String>) {
    runApplication<ParticleRedisModuleApplication>(*args)
}
