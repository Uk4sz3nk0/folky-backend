package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.messaging.specification.models.MessageRequestDto

/**
 * MessagingHandler
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

interface MessagingHandler {

    fun handleSendRequest(messageRequest: MessageRequestDto): MutableList<String>
}