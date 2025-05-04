package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.records.Pagination
import com.lukaszwodniak.folky.records.SortObject
import com.lukaszwodniak.folky.rest.dancing_team.specification.api.DancingTeamApi
import com.lukaszwodniak.folky.rest.specification.models.*
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * DancingTeamController
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@RestController
class DancingTeamController(
    private val dancingTeamHandler: DancingTeamHandler
) : DancingTeamApi {

    override fun addDancingTeam(dancingTeam: DancingTeamDto?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(dancingTeam?.let { dancingTeamHandler.handleAddTeam(it) })
    }

    override fun deleteTeam(teamId: Long?): ResponseEntity<Void> {
        teamId?.let { dancingTeamHandler.handleDeleteTeam(it) }
        return ResponseEntity.ok(null)
    }

    override fun getById(teamId: Long?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetById(it) })
    }

    override fun getByRegion(regionId: Long?): ResponseEntity<MutableList<DancingTeamDto>> {
        return ResponseEntity.ok(regionId?.let { dancingTeamHandler.handleGetByRegion(it) })
    }

    override fun getTeamDancers(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetTeamDancers(it) })
    }

    override fun getTeamDances(teamId: Long?): ResponseEntity<MutableList<DanceDto>> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetTeamDances(it) })
    }

    override fun getTeamMusicians(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetTeamMusicians(it) })
    }

    override fun getTeams(
        phrase: String?,
        page: Int?,
        size: Int?,
        orderBy: String?,
        direction: String?,
    ): ResponseEntity<PageDancingTeamListElementDto> {
        val pagination = Pagination(page ?: DEFAULT_PAGE_NUMBER, size ?: DEFAULT_PAGE_SIZE)
        val sort = SortObject(
            orderBy ?: DEFAULT_SORT_COLUMN,
            direction?.let { Sort.Direction.fromString(it) } ?: DEFAULT_SORT_DIRECTION)
        val teams = dancingTeamHandler.handleGetTeams(pagination, sort, phrase)
        return ResponseEntity.ok(teams)
    }

    override fun getFilteredTeams(
        phrase: String?,
        page: Int?,
        size: Int?,
        orderBy: String?,
        direction: String?,
        filterObject: FilterObjectDto?
    ): ResponseEntity<PageDancingTeamListElementDto> {
        val teams = dancingTeamHandler.handleGetTeams(
            Pagination(page ?: DEFAULT_PAGE_NUMBER, size ?: DEFAULT_PAGE_SIZE),
            SortObject(
                orderBy ?: DEFAULT_SORT_COLUMN,
                direction?.let { Sort.Direction.fromString(it) } ?: DEFAULT_SORT_DIRECTION),
            phrase,
            filterObject
        )
        return ResponseEntity.ok(teams)
    }

    override fun updateTeam(dancingTeam: DancingTeamDto?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(dancingTeam?.let { dancingTeamHandler.handleUpdateTeam(it) })
    }

    override fun addSubscription(dancingTeamId: Long?): ResponseEntity<Void> {
        dancingTeamId?.let { dancingTeamHandler.handleAddSubscription(it) }
        return ResponseEntity.ok().build()
    }

    override fun deleteSubscription(dancingTeamId: Long?): ResponseEntity<Void> {
        dancingTeamId?.let { dancingTeamHandler.handleDeleteSubscription(it) }
        return ResponseEntity.ok().build()
    }

    override fun getSubscribers(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        TODO("Not yet implemented")
    }

    override fun getGalleryImages(id: Long?): ResponseEntity<MutableList<String>> {
        val images = id?.let { dancingTeamHandler.handleGetGalleryImages(it) }
        return ResponseEntity.ok(images)
    }

    @EndpointLogger
    override fun getTeamEvents(
        id: Long?,
        page: Long?,
        size: Int?,
        connectionTypes: MutableList<String>?,
        eventTime: MutableList<String>?
    ): ResponseEntity<PagedEventsDto> {
        val events = id?.let {
            dancingTeamHandler.handleGetEvents(
                it,
                connectionTypes ?: emptyList(),
                page?.toInt() ?: DEFAULT_PAGE_NUMBER,
                size ?: DEFAULT_PAGE_SIZE,
                eventTime
            )
        }
        return ResponseEntity.ok(events)
    }

    companion object {
        private const val DEFAULT_PAGE_NUMBER: Int = 0
        private const val DEFAULT_PAGE_SIZE: Int = 10
        private const val DEFAULT_SORT_COLUMN: String = "id"
        private val DEFAULT_SORT_DIRECTION: Sort.Direction = Sort.Direction.DESC
    }
}