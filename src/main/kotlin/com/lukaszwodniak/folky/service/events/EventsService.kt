package com.lukaszwodniak.folky.service.events

import com.lukaszwodniak.folky.enums.EventConnectionType
import com.lukaszwodniak.folky.enums.EventTime
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Event
import com.lukaszwodniak.folky.records.CreatorResponse
import com.lukaszwodniak.folky.records.EventAdditionalData
import org.springframework.core.io.Resource
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

/**
 * EventsService
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

interface EventsService {

    fun addEvent(event: Event, eventAdditionalData: EventAdditionalData?)
    fun deleteEvent(id: Long)
    fun deleteEventPoster(id: Long)
    fun getEvent(id: Long, team: DancingTeam? = null): Event
    fun getEvents(page: Int, size: Int, phrase: String?): Page<Event>
    fun getPoster(id: Long): Resource?
    fun updateEvent(id: Long, event: Event, additionalData: EventAdditionalData?)
    fun updateOrAddPoster(id: Long, poster: MultipartFile)
    fun getTeamEvents(
        team: DancingTeam,
        page: Int,
        size: Int,
        connectionTypes: List<EventConnectionType>,
        eventTime: List<EventTime>
    ): Page<Event>

    fun addParticipant(event: Event, team: DancingTeam, withoutCheck: Boolean = false)
    fun deleteParticipant(event: Event, team: DancingTeam)
    fun getParticipants(event: Event): MutableList<DancingTeam>
    fun getCreator(eventId: Long): CreatorResponse
}