package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.SecurityHandler
import com.lukaszwodniak.folky.mapper.SecurityMapper
import com.lukaszwodniak.folky.rest.specification.models.*
import com.lukaszwodniak.folky.security.SecurityService
import com.lukaszwodniak.folky.service.users.UserService
import org.springframework.stereotype.Service

/**
 * SecurityHandler
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Service
class SecurityHandlerImpl(
    private val securityService: SecurityService,
    private val userService: UserService
) : SecurityHandler {

    override fun handleRegisterUser(registerUserRequestDto: RegisterUserRequestDto) {
        userService.registerUser(SecurityMapper.INSTANCE.mapRegisterRequest(registerUserRequestDto))
    }

    override fun handleLoginUser(loginRequest: LoginRequestDto): LoginResponseDto {
        val loginResponse = securityService.login(loginRequest.email, loginRequest.password)!!
        return SecurityMapper.INSTANCE.mapLoginResponse(loginResponse)
    }

    override fun handleRefreshToken(refreshTokenRequestDto: RefreshTokenRequestDto): RefreshTokenResponseDto {
        val refreshTokenResponse = securityService.exchangeRefreshToken(refreshTokenRequestDto.refreshToken!!)!!
        return SecurityMapper.INSTANCE.mapRefreshTokenResponse(refreshTokenResponse)
    }
}