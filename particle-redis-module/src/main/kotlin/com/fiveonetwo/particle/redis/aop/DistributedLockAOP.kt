package com.fiveonetwo.particle.redis.aop

import com.fiveonetwo.particle.redis.annotation.DistributedLock
import com.fiveonetwo.particle.redis.annotation.DistributedLocks
import com.fiveonetwo.particle.redis.parser.CustomSpringELParser
import com.fiveonetwo.particle.redis.shared.utils.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.springframework.stereotype.Component


@Aspect
@Component
class DistributedLockAOP(
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AOPForTransaction,
) {
    val logger: Logger = logger<DistributedLockAOP>()

    companion object {
        const val REDISSON_LOCK_PREFIX: String = "LOCK:"
    }

    @Around("@annotation(com.fiveonetwo.particle.redis.annotation.DistributedLock)")
    fun lock(processingJoinPoint: ProceedingJoinPoint): Any? {
        val signature = processingJoinPoint.signature as MethodSignature
        val method = signature.method
        val annotation = method.getAnnotation(DistributedLock::class.java)

        val key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
            parameterNames = signature.parameterNames,
            args = processingJoinPoint.args,
            key = annotation.key
        )
        val rLock = redissonClient.getLock(key)

        try {
            val isAvailable = rLock.tryLock(annotation.waitTime, annotation.expireTime, annotation.timeUnit)

            if (!isAvailable) {
                throw IllegalStateException("try lock fail")
            }

            logger.info("lock(key : $key) start")
            val result = aopForTransaction.process(processingJoinPoint)
            logger.info("lock(key : $key) end")
            return result
        } catch (exception: Exception) {
            logger.warn(exception.message, exception)
            throw IllegalStateException("execute fail during lock process")
        } finally {
            rLock.unlock()
        }
    }

    @Around("@annotation(com.fiveonetwo.particle.redis.annotation.DistributedLocks)")
    fun locks(processingJoinPoint: ProceedingJoinPoint): Any? {
        val signature = processingJoinPoint.signature as MethodSignature
        val method = signature.method
        val mainAnnotation = method.getAnnotation(DistributedLocks::class.java)

        val keys = mainAnnotation.values
            .map { it.key }
            .map { key ->
                REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
                    parameterNames = signature.parameterNames,
                    args = processingJoinPoint.args,
                    key = key
                )
            }

        val rLocks = keys.map { key -> redissonClient.getLock(key) }

        try {
            val results = rLocks.mapIndexed { index, rLock ->
                val annotation = mainAnnotation.values[index]
                rLock.tryLock(annotation.waitTime, annotation.expireTime, annotation.timeUnit)
            }
            val isAvailable = results.all { it }

            if (!isAvailable) {
                throw IllegalStateException("try lock fail")
            }

            logger.info("lock(key : [${keys.joinToString()}]) start")
            val result = aopForTransaction.process(processingJoinPoint)
            logger.info("lock(key : [${keys.joinToString()}]) end")
            return result
        } catch (exception: Exception) {
            logger.warn(exception.message, exception)
            throw IllegalStateException("execute fail during lock process")
        } finally {
            rLocks.forEach { rLock -> rLock.unlock() }
        }
    }
}