package com.lukaszwodniak.folky.service.utils

import com.lukaszwodniak.folky.enums.DeviceType
import org.springframework.stereotype.Service

/**
 * UtilsService
 *
 * Created on: 2024-12-21
 * @author Łukasz Wodniak
 */

@Service
interface UtilsService {

    fun addDeviceToken(token: String, deviceType: DeviceType): Boolean
}