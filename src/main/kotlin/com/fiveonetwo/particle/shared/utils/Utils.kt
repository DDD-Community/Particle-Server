package com.fiveonetwo.particle.shared.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.UUID.*


fun uuid(): String = randomUUID().toString()
inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)