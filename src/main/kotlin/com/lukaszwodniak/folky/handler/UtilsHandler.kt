package com.lukaszwodniak.folky.handler

/**
 * UtilsHandler
 *
 * Crated on: 2024-12-21
 * @author Łukasz Wodniak
 */

interface UtilsHandler {

    fun handleAddDeviceToken(token: String, deviceType: String): Boolean
}