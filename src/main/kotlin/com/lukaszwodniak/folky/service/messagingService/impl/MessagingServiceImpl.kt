package com.lukaszwodniak.folky.service.messagingService.impl

import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.lukaszwodniak.folky.records.MessageRequest
import com.lukaszwodniak.folky.service.messagingService.MessagingService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

/**
 * MessagingServiceImpl
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

@Service
class MessagingServiceImpl : MessagingService {

    override fun sendMessage(
        receiverToken: String,
        title: String,
        message: String,
        additionalData: Map<String, String>?
    ): String {
        val messageResponse = Message.builder().setToken(receiverToken)
            .setNotification(Notification.builder().setTitle(title).setBody(message).build()).putAllData(additionalData)
            .build()
        return messageResponse.toString()
    }

    @Async
    override fun sendMessage(messageRequest: MessageRequest): MutableList<String> {
        return messageRequest.receivers.mapTo(mutableListOf()) {
            sendMessage(
                it,
                title = messageRequest.title,
                message = messageRequest.message,
                additionalData = messageRequest.additionalData
            )
        }
    }
}