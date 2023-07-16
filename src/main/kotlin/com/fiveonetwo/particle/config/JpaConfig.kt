package com.fiveonetwo.particle.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["com.fiveonetwo.particle.domain.*.repository"])
@EnableJpaAuditing
class JpaConfig