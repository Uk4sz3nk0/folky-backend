package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.enums.UserType
import com.lukaszwodniak.folky.handler.SecurityHandler
import com.lukaszwodniak.folky.mapper.DancingTeamMapper
import com.lukaszwodniak.folky.mapper.SecurityMapper
import com.lukaszwodniak.folky.mapper.UserMapper
import com.lukaszwodniak.folky.repository.RegionRepository
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
    private val userService: UserService,
    private val regionRepository: RegionRepository,
) : SecurityHandler {

    override fun handleRegisterUser(registerUserRequestDto: RegisterUserRequestDto) {
        userService.registerUser(SecurityMapper.INSTANCE.mapRegisterRequest(registerUserRequestDto))
    }

    override fun handleRegisterDancingTeamUser(registerRequest: RegisterDancingTeamAccountRequestDto) {
        val mappedRequest = SecurityMapper.INSTANCE.mapRegisterDancingTeamRequest(registerRequest)
        val regionOptional =
            if (registerRequest.regionId == NO_REGION_ID) regionRepository.findById(1) else regionRepository.findById(
                registerRequest.regionId
            )
        val updatedRequest = regionOptional.map { region -> mappedRequest.copy(region = region) }.orElse(mappedRequest)
        userService.registerDancingTeamUser(updatedRequest)
    }

    override fun handleLoginUser(loginRequest: LoginRequestDto): LoginResponseDto {
        val loginResponse = securityService.login(loginRequest.email, loginRequest.password)!!
        val user = userService.getUserByEmail(loginRequest.email)
        val mappedResponse = SecurityMapper.INSTANCE.mapLoginResponse(loginResponse)
        mappedResponse.userData = user?.let { UserMapper.INSTANCE.mapUserData(it) }
        mappedResponse.dancingTeamData = user?.let {
            if (it.userType == UserType.DANCING_TEAM) {
                it.dancingTeam?.let { dancingTeam -> DancingTeamMapper.INSTANCE.mapDancingTeamData(dancingTeam) }
            } else {
                null
            }
        }
        return mappedResponse
    }

    override fun handleRefreshToken(refreshTokenRequestDto: RefreshTokenRequestDto): RefreshTokenResponseDto {
        val refreshTokenResponse = securityService.exchangeRefreshToken(refreshTokenRequestDto.refreshToken!!)!!
        return SecurityMapper.INSTANCE.mapRefreshTokenResponse(refreshTokenResponse)
    }

    companion object {
        private const val NO_REGION_ID = -1L
    }
}