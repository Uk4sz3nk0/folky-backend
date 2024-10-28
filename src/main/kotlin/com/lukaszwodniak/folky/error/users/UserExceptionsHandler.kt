package com.lukaszwodniak.folky.error.users

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * UserExceptionsHandler
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@RestControllerAdvice
class UserExceptionsHandler {

    @ExceptionHandler(EmailInUseException::class)
    fun handleEmailInUseException(e: EmailInUseException): ResponseEntity<Map<String, Any>> {
        logger.error("Error reason: ${e.message}")
        val errorResponse = mapOf(
            "code" to UsersErrorCode.EMAIL_IS_ALREADY_IN_USE.getCode(),
            "message" to (e.message ?: "Email is already in use")
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidLoginCredentialsException::class)
    fun handleInvalidLoginCredentialsException(e: InvalidLoginCredentialsException): ResponseEntity<Map<String, Any>> {
        logger.error("Error reason: ${e.message}")
        val errorResponse = mapOf(
            "code" to UsersErrorCode.INVALID_LOGIN_CREDENTIALS.getCode(),
            "message" to (e.message ?: "Invalid login credentials")
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    fun handleInvalidRefreshTokenException(e: InvalidRefreshTokenException): ResponseEntity<Map<String, Any>> {
        logger.error("Error reason: ${e.message}")
        val errorResponse = mapOf(
            "code" to UsersErrorCode.INVALID_REFRESH_TOKEN.getCode(),
            "message" to (e.message ?: "Invalid refresh token")
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(UserExceptionsHandler::class.java)
    }
}