package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.MessagingHandler
import com.lukaszwodniak.folky.mapper.MessagingMapper
import com.lukaszwodniak.folky.rest.messaging.specification.models.MessageRequestDto
import com.lukaszwodniak.folky.service.messagingService.MessagingService
import org.springframework.stereotype.Service

/**
 * MessagingHandlerImpl
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

@Service
class MessagingHandlerImpl(
    val messagingService: MessagingService
) : MessagingHandler {

    override fun handleSendRequest(messageRequest: MessageRequestDto): MutableList<String> {
        return messagingService.sendMessage(MessagingMapper.INSTANCE.map(messageRequest))
    }
}