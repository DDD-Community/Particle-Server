package com.fiveonetwo.particle.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {
//    @Bean
//    fun objectMapper() = ObjectMapper().apply {
//        registerModule(KotlinModule.Builder()
//                .withReflectionCacheSize(512)
//                .configure(KotlinFeature.NullToEmptyCollection, true)
//                .configure(KotlinFeature.NullToEmptyMap, true)
//                .configure(KotlinFeature.NullIsSameAsDefault, true)
//                .configure(KotlinFeature.SingletonSupport, true)
//                .configure(KotlinFeature.StrictNullChecks, true)
//                .build())
//    }
}