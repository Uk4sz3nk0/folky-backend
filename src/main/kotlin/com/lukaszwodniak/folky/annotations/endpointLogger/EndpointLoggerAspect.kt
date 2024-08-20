package com.lukaszwodniak.folky.annotations.endpointLogger

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * EndpointLoggerAspect
 *
 * Created on: 2024-08-20
 * @author Łukasz Wodniak
 */

@Aspect
@Component
class EndpointLoggerAspect {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Before("@annotation(com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger)")
    fun logMethodExecution(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        logEndpoint(methodName)
    }

    private fun logEndpoint(name: String) {
        logger.info("Request \"$name\" has called")
    }
}