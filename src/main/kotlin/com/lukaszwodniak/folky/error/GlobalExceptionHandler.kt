package com.lukaszwodniak.folky.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * GlobalExceptionHandler
 *
 * Created on: 2024-08-20
 * @author Łukasz Wodniak
 */

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchDancingTeamException::class)
    fun handleNoSuchDancingTeam(exception: NoSuchDancingTeamException): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.NOT_FOUND)
    }
}