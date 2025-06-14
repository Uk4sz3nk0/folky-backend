package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.records.Pagination
import com.lukaszwodniak.folky.records.SortObject
import com.lukaszwodniak.folky.rest.dancing_team.specification.api.DancingTeamApi
import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto
import com.lukaszwodniak.folky.rest.specification.models.*
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @EndpointLogger
    override fun getTeamDances(id: Long?, page: Int?, size: Int?): ResponseEntity<PagedDancesDto> {
        val teamDances = id?.let {
            dancingTeamHandler.handleGetTeamDances(
                it,
                page ?: ControllerCommons.DEFAULT_PAGE,
                size ?: ControllerCommons.DEFAULT_PAGE_SIZE
            )
        }
        return ResponseEntity.ok(teamDances)
    }

    override fun getTeams(
        phrase: String?,
        page: Int?,
        size: Int?,
        orderBy: String?,
        direction: String?,
    ): ResponseEntity<PageDancingTeamListElementDto> {
        val pagination =
            Pagination(page ?: ControllerCommons.DEFAULT_PAGE, size ?: ControllerCommons.DEFAULT_PAGE_SIZE)
        val sort = SortObject(
            orderBy ?: ControllerCommons.DEFAULT_SORT_COLUMN,
            direction?.let { Sort.Direction.fromString(it) } ?: ControllerCommons.DEFAULT_SORT_DIRECTION)
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
            Pagination(page ?: ControllerCommons.DEFAULT_PAGE, size ?: ControllerCommons.DEFAULT_PAGE_SIZE),
            SortObject(
                orderBy ?: ControllerCommons.DEFAULT_SORT_COLUMN,
                direction?.let { Sort.Direction.fromString(it) } ?: ControllerCommons.DEFAULT_SORT_DIRECTION),
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
                page?.toInt() ?: ControllerCommons.DEFAULT_PAGE,
                size ?: ControllerCommons.DEFAULT_PAGE_SIZE,
                eventTime
            )
        }
        return ResponseEntity.ok(events)
    }

    @EndpointLogger
    override fun getTeamAchievements(id: Long?, page: Int?, size: Int?): ResponseEntity<PagedAchievementsDto> {
        val achievements = id?.let {
            dancingTeamHandler.handleGetTeamAchievements(
                it,
                page ?: ControllerCommons.DEFAULT_PAGE,
                size ?: ControllerCommons.DEFAULT_PAGE_SIZE
            )
        }
        return ResponseEntity.ok(achievements)
    }

    @EndpointLogger
    override fun updateTeamDances(id: Long?, dances: MutableList<DanceDto>?): ResponseEntity<Void> {
        id?.let { teamId ->
            dances?.let {
                dancingTeamHandler.handleUpdateTeamDances(teamId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    @GetMapping("/api/teams/{id}/people")
    fun getPeople(
        @PathVariable id: Long?,
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?,
        @RequestParam("phrase") phrase: String?
    ): ResponseEntity<PagedPeopleDto> {
        val people = id?.let {
            dancingTeamHandler.handleGetTeamPeople(
                it,
                page ?: ControllerCommons.DEFAULT_PAGE,
                size ?: ControllerCommons.DEFAULT_PAGE_SIZE,
                phrase
            )
        }

        return ResponseEntity.ok(people)
    }

    @EndpointLogger
    @PutMapping("/api/teams/{id}/people")
    fun setTeamPeople(@PathVariable("id") id: Long?, @RequestBody people: List<PersonDto>?): ResponseEntity<Void> {
        id?.let { teamId ->
            people?.let {
                dancingTeamHandler.handleSetTeamPeople(teamId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

}