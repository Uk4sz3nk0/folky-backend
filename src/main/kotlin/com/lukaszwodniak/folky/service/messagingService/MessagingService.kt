package com.lukaszwodniak.folky.service.messagingService

import com.lukaszwodniak.folky.records.MessageRequest

/**
 * MessagingService
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

interface MessagingService {

    /**
     * This method is called from controller
     */
    fun sendMessage(
        receiverToken: String,
        title: String,
        message: String,
        additionalData: Map<String, String>? = null
    ): String


    /**
     * This method is called in logic in app
     */
    fun sendMessage(messageRequest: MessageRequest): MutableList<String>
}