package com.lukaszwodniak.folky.service.utils.impl

import com.lukaszwodniak.folky.enums.DeviceType
import com.lukaszwodniak.folky.model.DeviceToken
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.repository.DeviceTokenRepository
import com.lukaszwodniak.folky.service.users.UserService
import com.lukaszwodniak.folky.service.utils.UtilsService
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * UtilsService
 *
 * Created on: 2024-12-21
 * @author Łukasz Wodniak
 */

@Service
class UtilsServiceImpl(
    private val userService: UserService,
    private val deviceTokenRepository: DeviceTokenRepository
) : UtilsService {

    override fun addDeviceToken(token: String, deviceType: DeviceType): Boolean {
        val user = userService.getUserFromContext()
        val isTokenExists: Boolean =
            user?.let { deviceTokenRepository.existsDeviceTokenByTokenAndUser(token, it) } ?: false
        if (!isTokenExists) {
            val deviceToken = createDeviceToken(token, deviceType, user!!)
            deviceTokenRepository.saveAndFlush(deviceToken)
        }
        return isTokenExists
    }

    private fun createDeviceToken(token: String, deviceType: DeviceType, user: User): DeviceToken = DeviceToken(
        null,
        token,
        deviceType,
        Instant.now(),
        user
    )

}