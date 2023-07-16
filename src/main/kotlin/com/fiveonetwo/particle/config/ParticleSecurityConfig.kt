package com.fiveonetwo.particle.config

import com.fiveonetwo.particle.shared.security.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class ParticleSecurityConfig(
        private val jwtFilter: JwtFilter
) {
    @Bean
    fun userDetailsService() = UserDetailsService { _ -> null }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
                .authorizeHttpRequests { auth ->
                    auth
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            .anyRequest().denyAll()
                }
                .httpBasic { it.disable() }
                .csrf { it.disable() }
                .build()
    }
}