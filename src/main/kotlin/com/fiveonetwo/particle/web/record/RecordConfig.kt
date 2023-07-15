package com.fiveonetwo.particle.web.record

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter


@Configuration
class RecordConfig(
    private val recordHandler: RecordHandler
) {

    @Bean
    fun recordRouter() = coRouter {
        "/records".nest {
            POST("") { reqeust -> recordHandler.createRecord(reqeust) }
        }
    }

}