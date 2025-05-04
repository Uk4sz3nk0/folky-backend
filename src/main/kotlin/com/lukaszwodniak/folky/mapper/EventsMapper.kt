package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Address
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Event
import com.lukaszwodniak.folky.records.CreatorResponse
import com.lukaszwodniak.folky.rest.events.specification.models.*
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * EventsMapper
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@Mapper
interface EventsMapper {

    @Mapping(source = "address.id", target = "addressId")
    fun mapToDto(event: Event): EventDto

    @Mapping(target = "connectionTypes", ignore = true)
    @Mapping(target = "eti", ignore = true)
    fun mapFromDto(event: EventDto): Event
    fun mapFromDtoList(events: List<EventDto>): List<Event>
    fun mapToDtoList(events: List<Event>): List<EventDto>

    fun mapToPageableDto(pageable: Page<Event>): PagedEventsDto

    fun mapAddressFromDto(address: AddressDto): Address

    fun mapParticipantsToDto(participants: MutableList<DancingTeam>): MutableList<DancingTeamListElementDto>

    @Mapping(source = "creator", target = "object")
    fun mapCreatorResponseToDto(response: CreatorResponse): EventCreatorResponseDto

    companion object {
        val INSTANCE: EventsMapper = Mappers.getMapper(EventsMapper::class.java)
    }
}