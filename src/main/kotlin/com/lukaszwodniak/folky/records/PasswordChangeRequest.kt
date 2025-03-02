package com.lukaszwodniak.folky.records

/**
 * PasswordChangeRequest
 *
 * Created on: 2025-03-02
 * @author Łukasz Wodniak
 */

data class PasswordChangeRequest(
    val oldPassword: String,
    val newPassword: String
)
