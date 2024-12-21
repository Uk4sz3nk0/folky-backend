package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.handler.SecurityHandler
import com.lukaszwodniak.folky.handler.UtilsHandler
import com.lukaszwodniak.folky.rest.specification.models.*
import com.lukaszwodniak.folky.rest.user.specification.api.UserApi
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * UserController
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@RestController
class UserController(
    private val securityHandler: SecurityHandler,
    private val utilsHandler: UtilsHandler
) : UserApi {

    override fun addUser(body: UserDto?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: Long?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun editUser(body: UserDto?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getUserById(id: Long?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getUserSubscriptions(): ResponseEntity<MutableList<DancingTeamDto>> {
        TODO("Not yet implemented")
    }

    override fun loginUser(loginRequest: LoginRequestDto?): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(loginRequest?.let {
            securityHandler.handleLoginUser(it)
        })
    }

    override fun refreshToken(refreshTokenRequest: RefreshTokenRequestDto?): ResponseEntity<RefreshTokenResponseDto> {
        return ResponseEntity.ok(refreshTokenRequest?.let {
            securityHandler.handleRefreshToken(it)
        })
    }

    override fun registerUser(registerUserRequest: RegisterUserRequestDto?): ResponseEntity<Void> {
        registerUserRequest?.let {
            securityHandler.handleRegisterUser(it)
        }
        return ResponseEntity.ok().build()
    }

    override fun registerAsDancingTeam(dancingTeamBody: RegisterDancingTeamAccountRequestDto?): ResponseEntity<Void> {
        logger.debug("Request \"registerAsDancingTeam\" has called")
        dancingTeamBody?.let {
            securityHandler.handleRegisterDancingTeamUser(it)
        }
        return ResponseEntity.ok().build()
    }

    override fun addDeviceToken(token: String?, deviceType: String?): ResponseEntity<Void> {
        logger.debug("Request \"addDeviceToken\" has called")
        val hasTokenExists = token?.let { utilsHandler.handleAddDeviceToken(it, deviceType!!) }!!
        return if (hasTokenExists) {
            ResponseEntity.status(201).build()
        } else {
            ResponseEntity.ok().build()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(UserController::class.java)
    }
}