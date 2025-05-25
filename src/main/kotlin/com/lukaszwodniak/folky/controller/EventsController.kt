package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.EventsHandler
import com.lukaszwodniak.folky.rest.events.specification.api.EventsApi
import com.lukaszwodniak.folky.rest.events.specification.models.*
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * EventsController
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@RestController
class EventsController(
    private val eventsHandler: EventsHandler,
) : EventsApi {

    @EndpointLogger
    override fun addEvent(eventRequest: EventRequestDto?): ResponseEntity<Void> {
        eventRequest?.let { eventsHandler.handleAddEvent(it) }
        return ResponseEntity.noContent().build()
    }

    @EndpointLogger
    override fun deleteEvent(id: Long?): ResponseEntity<Void> {
        id?.let { eventsHandler.handleDeleteEvent(it) }
        return ResponseEntity.noContent().build()
    }

    @EndpointLogger
    override fun deleteEventPoster(id: Long?): ResponseEntity<Void> {
        id?.let { eventsHandler.handleDeleteEventPoster(it) }
        return ResponseEntity.noContent().build()
    }

    @EndpointLogger
    override fun getEvent(id: Long?, teamId: Long?): ResponseEntity<EventDto> {
        val event = id?.let { eventsHandler.handleGetEvent(it, teamId) }
        return ResponseEntity.ok(event)
    }

    @EndpointLogger
    override fun getEvents(page: Int?, size: Int?, phrase: String?): ResponseEntity<PagedEventsDto> {
        val events = eventsHandler.handleGetEvents(page ?: DEFAULT_PAGE_NUMBER, size ?: DEFAULT_PAGE_SIZE, phrase)
        return ResponseEntity.ok(events)
    }

    @EndpointLogger
    override fun getPoster(id: Long?): ResponseEntity<Resource> {
        val poster = id?.let { eventsHandler.handleGetPoster(it) }
        return ResponseEntity.ok(poster)
    }

    @EndpointLogger
    override fun updateEvent(id: Long?, eventRequest: EventRequestDto?): ResponseEntity<Void> {
        id?.let { eventId ->
            eventRequest?.let {
                eventsHandler.handleUpdateEvent(eventId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun updatePoster(id: Long?, poster: MultipartFile?): ResponseEntity<Void> {
        id?.let { eventId ->
            poster?.let {
                eventsHandler.handleUpdatePoster(eventId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getParticipants(id: Long?): ResponseEntity<MutableList<DancingTeamListElementDto>> {
        val participants = id?.let { eventsHandler.handleGetParticipants(it) }
        return ResponseEntity.ok(participants)
    }

    @EndpointLogger
    override fun addParticipant(id: Long?, participantId: Long?): ResponseEntity<Void> {
        id?.let { eventId ->
            participantId?.let {
                eventsHandler.handleAddParticipant(eventId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deleteParticipant(id: Long?, participantId: Long?): ResponseEntity<Void> {
        id?.let { eventId ->
            participantId?.let {
                eventsHandler.handleDeleteParticipant(eventId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getCreator(id: Long?): ResponseEntity<EventCreatorResponseDto> {
        val response = id?.let { eventsHandler.handleGetCreator(it) }
        return ResponseEntity.ok(response)
    }

    companion object {
        private const val DEFAULT_PAGE_NUMBER: Int = 0
        private const val DEFAULT_PAGE_SIZE: Int = 10
    }
}