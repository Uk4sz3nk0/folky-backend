package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.EventsHandler
import com.lukaszwodniak.folky.mapper.EventsMapper
import com.lukaszwodniak.folky.records.EventAdditionalData
import com.lukaszwodniak.folky.rest.events.specification.models.*
import com.lukaszwodniak.folky.service.address.AddressService
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.events.EventsService
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * EventsHandlerImpl
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@Service
class EventsHandlerImpl(
    private val eventsService: EventsService,
    private val addressService: AddressService,
    private val dancingTeamService: DancingTeamService
) : EventsHandler {

    override fun handleAddEvent(eventRequest: EventRequestDto) {
        val event = eventRequest.event
        val address = eventRequest.address
        val mappedEvent = EventsMapper.INSTANCE.mapFromDto(event)
        val additionalData =
            EventAdditionalData(event.isIsFreeEntry, event.isIsParticipantToo, eventRequest.event.creatorId)
        eventsService.addEvent(
            mappedEvent.copy(address = EventsMapper.INSTANCE.mapAddressFromDto(address)),
            additionalData
        )
    }

    override fun handleDeleteEvent(id: Long) {
        eventsService.deleteEvent(id)
    }

    override fun handleDeleteEventPoster(id: Long) {
        eventsService.deleteEventPoster(id)
    }

    override fun handleGetEvent(id: Long, teamId: Long?): EventDto {
        val dancingTeam = teamId?.let { dancingTeamService.getById(it) }
        val event = eventsService.getEvent(id, dancingTeam)
        return EventsMapper.INSTANCE.mapToDto(event)
    }

    override fun handleGetEvents(page: Int, size: Int, phrase: String?): PagedEventsDto {
        val events = eventsService.getEvents(page, size, phrase)
        return EventsMapper.INSTANCE.mapToPageableDto(events)
    }

    override fun handleGetPoster(id: Long): Resource? {
        return eventsService.getPoster(id)
    }

    override fun handleUpdateEvent(id: Long, eventRequest: EventRequestDto) {
        val event = eventRequest.event
        val address = eventRequest.address
        val mappedEvent = EventsMapper.INSTANCE.mapFromDto(event)
        val mappedAddress = EventsMapper.INSTANCE.mapAddressFromDto(address)
        val additionalData = EventAdditionalData(event.isIsFreeEntry, event.isIsParticipantToo, event.creatorId)
        eventsService.updateEvent(id, mappedEvent.copy(address = mappedAddress), additionalData)
    }

    override fun handleUpdatePoster(id: Long, poster: MultipartFile) {
        eventsService.updateOrAddPoster(id, poster)
    }

    override fun handleAddParticipant(id: Long, teamId: Long) {
        val event = eventsService.getEvent(id)
        val team = dancingTeamService.getById(teamId)
        eventsService.addParticipant(event, team)
    }

    override fun handleDeleteParticipant(id: Long, teamId: Long) {
        val event = eventsService.getEvent(id)
        val team = dancingTeamService.getById(teamId)
        eventsService.deleteParticipant(event, team)
    }

    override fun handleGetParticipants(id: Long): MutableList<DancingTeamListElementDto> {
        val event = eventsService.getEvent(id)
        val participants = eventsService.getParticipants(event)
        return EventsMapper.INSTANCE.mapParticipantsToDto(participants)
    }

    override fun handleGetCreator(id: Long): EventCreatorResponseDto {
        val response = eventsService.getCreator(id)
        return EventsMapper.INSTANCE.mapCreatorResponseToDto(response)
    }
}