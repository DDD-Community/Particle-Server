package com.fiveonetwo.particle.config

import com.fiveonetwo.particle.shared.security.filter.JwtFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono


@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Value("\${security.username}") private val username: String,
    @Value("\${security.password}") private val password: String,
    private val jwtFilter: JwtFilter,
) {

    @Bean
    fun userDetailsService() = MapReactiveUserDetailsService(
        User.withUsername(username)
            .password(password)
            .build()
    )

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { auth ->
            auth
                .pathMatchers("/auth/login").permitAll()
                .pathMatchers("/records/**").authenticated()
                .anyExchange().denyAll()
        }
            .addFilterAt(jwtFilter, SecurityWebFiltersOrder.HTTP_BASIC)
            .exceptionHandling { handler ->
                handler.authenticationEntryPoint { exchange, ex ->
                    Mono.fromRunnable {
                        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                    }
                }
                handler.accessDeniedHandler { exchange, denied ->
                    Mono.fromRunnable {
                        exchange.response.statusCode = HttpStatus.FORBIDDEN
                    }
                }
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
        return http.build()
    }
}