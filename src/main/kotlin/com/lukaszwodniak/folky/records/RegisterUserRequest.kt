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
    val brithDate: LocalDate? = LocalDate.now(),
    val howLongDancing: Int? = 0,
    val howLongPlayingInstruments: Int? = 0,
    val preferredLanguage: String? = "pl_PL",
    val wantReceivePushNotifications: Boolean? = false,
    val wantReceiveEmailNotifications: Boolean? = false
)
