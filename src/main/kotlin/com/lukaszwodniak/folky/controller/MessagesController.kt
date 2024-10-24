package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.MessagingHandler
import com.lukaszwodniak.folky.rest.messaging.specification.api.CloudMessagesApi
import com.lukaszwodniak.folky.rest.messaging.specification.models.MessageRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * MessagesController
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

@RestController
class MessagesController(
    val messagingHandler: MessagingHandler
) : CloudMessagesApi {

    @EndpointLogger
    override fun sendMessage(messageRequest: MessageRequestDto?): ResponseEntity<MutableList<String>> {
        val responseData = messageRequest?.let {
            messagingHandler.handleSendRequest(it)
        }
        return ResponseEntity.ok(responseData)
    }

    override fun addDeviceToken(token: String?, deviceType: String?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun addSubscription(dancingTeamId: Long?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteDeviceToken(tokenId: Long?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun deleteSubscription(subscriptionId: Long?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }
}