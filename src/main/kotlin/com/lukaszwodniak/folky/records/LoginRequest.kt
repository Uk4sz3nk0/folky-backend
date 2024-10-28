package com.lukaszwodniak.folky.records

/**
 * LoginRequest
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

data class LoginRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)
