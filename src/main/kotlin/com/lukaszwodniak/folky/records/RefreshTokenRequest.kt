package com.lukaszwodniak.folky.records

/**
 * RefreshTokenRequest
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

data class RefreshTokenRequest(
    val refreshToken: String,
    val grantType: String
)
