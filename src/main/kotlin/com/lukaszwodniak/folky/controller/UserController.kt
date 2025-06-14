package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.handler.SecurityHandler
import com.lukaszwodniak.folky.handler.UsersHandler
import com.lukaszwodniak.folky.handler.UtilsHandler
import com.lukaszwodniak.folky.records.DancingTeamFiles
import com.lukaszwodniak.folky.records.RegisterDancingTeamUserRequest
import com.lukaszwodniak.folky.rest.specification.models.*
import com.lukaszwodniak.folky.rest.user.specification.api.UserApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * UserController
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@RestController
class UserController(
    private val securityHandler: SecurityHandler,
    private val utilsHandler: UtilsHandler,
    private val dancingTeamHandler: DancingTeamHandler,
    private val usersHandler: UsersHandler,
) : UserApi {

    override fun addUser(user: UserDto?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: Long?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    @EndpointLogger
    override fun editUser(id: Long?, userData: UserDataDto?): ResponseEntity<UserDataDto> {
        val user = userData?.let {
            usersHandler.editUser(id!!, it)
        }
        return ResponseEntity.ok(user)
    }

    @EndpointLogger
    override fun getUser(id: Long?): ResponseEntity<UserDataDto> {
        val user = id?.let { usersHandler.getUserById(it) }
        return ResponseEntity.ok().body(user)
    }

    @EndpointLogger
    override fun getUserSubscriptions(
        id: Long?,
        page: Int?,
        size: Int?
    ): ResponseEntity<PageDancingTeamListElementDto> {
        val subscriptions =
            dancingTeamHandler.handleGetSubscribedTeams(id!!, page ?: DEFAULT_PAGE, size ?: DEFAULT_SIZE)
        return ResponseEntity.ok(subscriptions)
    }

    @EndpointLogger
    override fun loginUser(loginRequest: LoginRequestDto?): ResponseEntity<LoginResponseDto> {
        val loginResponse = loginRequest?.let {
            securityHandler.handleLoginUser(it)
        }
        return ResponseEntity.ok(loginResponse)
    }

    @EndpointLogger
    override fun refreshToken(refreshTokenRequest: RefreshTokenRequestDto?): ResponseEntity<RefreshTokenResponseDto> {
        return ResponseEntity.ok(refreshTokenRequest?.let {
            securityHandler.handleRefreshToken(it)
        })
    }

    @EndpointLogger
    override fun registerUser(registerUserRequest: RegisterUserRequestDto?): ResponseEntity<Void> {
        registerUserRequest?.let {
            securityHandler.handleRegisterUser(it)
        }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun registerAsDancingTeam(
        email: String?,
        password: String?,
        teamName: String?,
        teamDescription: String?,
        creationYear: Int?,
        regionId: Long?,
        city: String?,
        street: String?,
        houseNumber: String?,
        @RequestPart logo: MultipartFile?,
        @RequestPart banner: MultipartFile?,
        wantReceiveEmailNotifications: Boolean?,
        wantReceivePushNotifications: Boolean?,
    ): ResponseEntity<Void> {
        val registerRequest = RegisterDancingTeamUserRequest(
            email = email ?: "", // Temp empty string
            password = password ?: "", // Temp empty string
            teamName = teamName ?: "", // Temp empty string
            teamDescription = teamDescription,
            creationYear = creationYear,
            city = city,
            street = street,
            homeNumber = houseNumber,
            files = DancingTeamFiles(
                logo = logo,
                banner = banner,
            )
        )
        securityHandler.handleRegisterDancingTeamAsUser(registerRequest, regionId ?: -1)
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun addDeviceToken(token: String?, deviceType: String?): ResponseEntity<Void> {
        val hasTokenExists = token?.let { utilsHandler.handleAddDeviceToken(it, deviceType!!) }!!
        return if (hasTokenExists) {
            ResponseEntity.status(201).build()
        } else {
            ResponseEntity.ok().build()
        }
    }

    @EndpointLogger
    override fun deleteDeviceToken(token: String?, deviceType: String?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    @EndpointLogger
    override fun getUsers(page: Int?, size: Int?, phrase: String?): ResponseEntity<PageUserDto> {
        val users = usersHandler.handleGetUsers(page ?: DEFAULT_PAGE, size ?: DEFAULT_SIZE, phrase)
        return ResponseEntity.ok(users)
    }

    @EndpointLogger
    override fun changePassword(changePasswordRequest: ChangePasswordRequestDto?): ResponseEntity<Void> {
        changePasswordRequest?.let {
            usersHandler.handleChangePassword(changePasswordRequest)
        }
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val DEFAULT_SIZE: Int = 10
    }
}