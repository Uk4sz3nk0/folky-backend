package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.records.RegisterDancingTeamUserRequest
import com.lukaszwodniak.folky.rest.specification.models.*

/**
 * SecurityHandler
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

interface SecurityHandler {

    fun handleRegisterUser(registerUserRequestDto: RegisterUserRequestDto)
    fun handleRegisterDancingTeamUser(registerRequest: RegisterDancingTeamAccountRequestDto)
    fun handleRegisterDancingTeamAsUser(registerRequest: RegisterDancingTeamUserRequest, regionId: Long)
    fun handleLoginUser(loginRequest: LoginRequestDto): LoginResponseDto
    fun handleRefreshToken(refreshTokenRequestDto: RefreshTokenRequestDto): RefreshTokenResponseDto
}