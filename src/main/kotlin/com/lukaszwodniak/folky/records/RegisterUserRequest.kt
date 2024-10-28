package com.lukaszwodniak.folky.records

import java.time.LocalDate

/**
 * RegisterUserRequest
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

data class RegisterUserRequest(
    val email: String?,
    val password: String?,
    val firstName: String?,
    val lastName: String?,
    val brithDate: LocalDate?,
    val howLongDancing: Int?,
    val howLongPlayingInstruments: Int?,
    val preferredLanguage: String? = "pl_PL",
    val wantReceivePushNotifications: Boolean? = false,
    val wantReceiveEmailNotifications: Boolean? = false
)
