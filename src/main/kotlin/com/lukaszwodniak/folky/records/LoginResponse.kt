package com.lukaszwodniak.folky.records

/**
 * LoginResponse
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

data class LoginResponse(
    val idToken: String?,
    val refreshToken: String?
)
