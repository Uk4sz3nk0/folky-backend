package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.records.MessageRequest
import com.lukaszwodniak.folky.rest.messaging.specification.models.MessageRequestDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * MessagingMapper
 *
 * Created on: 2024-10-24
 * @author Łukasz Wodniak
 */

@Mapper
interface MessagingMapper {

    fun map(request: MessageRequestDto): MessageRequest

    companion object {
        val INSTANCE: MessagingMapper = Mappers.getMapper(MessagingMapper::class.java)
    }
}