package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.enums.DeviceType
import com.lukaszwodniak.folky.handler.UtilsHandler
import com.lukaszwodniak.folky.service.utils.UtilsService
import org.springframework.stereotype.Service

/**
 * UtilsHandlerImpl
 *
 * Created on: 2024-12-21
 * @author Łukasz Wodniak
 */

@Service
class UtilsHandlerImpl(
    private val utilsService: UtilsService
) : UtilsHandler {

    override fun handleAddDeviceToken(token: String, deviceType: String): Boolean {
        val type = DeviceType.valueOf(deviceType)
        return utilsService.addDeviceToken(token, type)
    }
}