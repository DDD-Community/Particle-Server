package com.fiveonetwo.particle.redis.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class AOPForTransaction {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun process(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        return proceedingJoinPoint.proceed()
    }
}