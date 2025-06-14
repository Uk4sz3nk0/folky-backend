package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.events.specification.models.*


/**
 * EventsHandler
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

interface EventsHandler {

    fun handleAddEvent(eventRequest: EventRequestDto)
    fun handleDeleteEvent(id: Long)
    fun handleGetEvent(id: Long, teamId: Long?): EventDto
    fun handleGetEvents(page: Int, size: Int, phrase: String?): PagedEventsDto
    fun handleUpdateEvent(id: Long, eventRequest: EventRequestDto)
    fun handleAddParticipant(id: Long, teamId: Long)
    fun handleDeleteParticipant(id: Long, teamId: Long)
    fun handleGetParticipants(id: Long): MutableList<DancingTeamListElementDto>
    fun handleGetCreator(id: Long): EventCreatorResponseDto
}