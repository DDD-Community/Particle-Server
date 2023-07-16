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
            POST("/") { request -> recordHandler.createRecord(request) }
            GET("/{recordId}") { request -> recordHandler.getRecordById(request) }
            GET("/my") { request -> recordHandler.getMyRecords(request) }
        }
    }
}