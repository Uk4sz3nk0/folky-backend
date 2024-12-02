package com.lukaszwodniak.folky.records

import com.lukaszwodniak.folky.model.Region

/**
 * RegisterDancingTeamUserRequest
 *
 * Created on: 2024-12-08
 * @author Łukasz Wodniak
 */

data class RegisterDancingTeamUserRequest(
    val email: String,
    val password: String,
    val teamName: String,
    val teamDescription: String? = "",
    val creationYear: Int? = 0,
    val region: Region? = null,
    val city: String? = "",
    val street: String? = "",
    val homeNumber: String? = ""
)
