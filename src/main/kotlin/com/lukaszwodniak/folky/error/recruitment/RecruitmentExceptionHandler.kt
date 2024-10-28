package com.lukaszwodniak.folky.error.recruitment

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice

/**
 * RecruitmentExceptionHandler
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Order(1)
@ControllerAdvice
class RecruitmentExceptionHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}