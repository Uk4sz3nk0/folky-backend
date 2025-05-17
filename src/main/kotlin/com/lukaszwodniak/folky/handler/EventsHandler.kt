package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.events.specification.models.DancingTeamListElementDto
import com.lukaszwodniak.folky.rest.events.specification.models.EventCreatorResponseDto
import com.lukaszwodniak.folky.rest.events.specification.models.EventDto
import com.lukaszwodniak.folky.rest.events.specification.models.EventRequestDto
import com.lukaszwodniak.folky.rest.events.specification.models.PagedEventsDto
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile


/**
 * EventsHandler
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

interface EventsHandler {

    fun handleAddEvent(eventRequest: EventRequestDto)
    fun handleDeleteEvent(id: Long)
    fun handleDeleteEventPoster(id: Long)
    fun handleGetEvent(id: Long, teamId: Long?): EventDto
    fun handleGetEvents(page: Int, size: Int, phrase: String?): PagedEventsDto
    fun handleGetPoster(id: Long): Resource?
    fun handleUpdateEvent(id: Long, eventRequest: EventRequestDto)
    fun handleUpdatePoster(id: Long, poster: MultipartFile)
    fun handleAddParticipant(id: Long, teamId: Long)
    fun handleDeleteParticipant(id: Long, teamId: Long)
    fun handleGetParticipants(id: Long): MutableList<DancingTeamListElementDto>
    fun handleGetCreator(id: Long): EventCreatorResponseDto
}