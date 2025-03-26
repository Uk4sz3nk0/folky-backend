package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.DeviceToken
import com.lukaszwodniak.folky.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * DeviceTokenRepository
 *
 * Created on: 2024-12-21
 * @author Łukasz Wodniak
 */

@Repository
interface DeviceTokenRepository : JpaRepository<DeviceToken, Long> {

    fun existsDeviceTokenByTokenAndUser(token: String, user: User): Boolean
}