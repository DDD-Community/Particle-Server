package com.fiveonetwo.particle.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {
    @Bean
    fun openapi(): OpenAPI {
        val info = Info()
                .version("v1")
                .title("particle API")
                .description("particle backend server 입니다.")

        val jwtSchemeName = "bearer token"
        val securityRequirement: SecurityRequirement = SecurityRequirement().addList(jwtSchemeName)
        val components = Components()
                .addSecuritySchemes(
                        jwtSchemeName, SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )

        val deploy = Server()
        deploy.url = "https://particle.k-net.kr"

        val test = Server()
        test.url = "http://localhost:8555"

        return OpenAPI()
                .info(info)
                .servers(listOf(deploy, test))
                .addSecurityItem(securityRequirement)
                .components(components)
    }
}

