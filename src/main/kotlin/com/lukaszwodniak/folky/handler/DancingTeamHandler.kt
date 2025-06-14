package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.records.Pagination
import com.lukaszwodniak.folky.records.SortObject
import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto
import com.lukaszwodniak.folky.rest.specification.models.*

/**
 * DancingTeamHandler
 *
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

interface DancingTeamHandler {

    fun handleAddTeam(team: DancingTeamDto): DancingTeamDto
    fun handleUpdateTeam(team: DancingTeamDto): DancingTeamDto
    fun handleDeleteTeam(teamId: Long)
    fun handleGetById(teamId: Long): DancingTeamDto
    fun handleGetTeamDances(teamId: Long, page: Int, size: Int): PagedDancesDto
    fun handleGetTeams(
        pagination: Pagination,
        sortObject: SortObject,
        searchPhrase: String?,
        filterObject: FilterObjectDto? = null
    ): PageDancingTeamListElementDto

    fun handleGetSubscribedTeams(id: Long, page: Int, size: Int): PageDancingTeamListElementDto
    fun handleAddSubscription(teamId: Long)
    fun handleDeleteSubscription(teamId: Long)
    fun handleGetGalleryImages(id: Long): MutableList<String>
    fun handleGetEvents(
        id: Long,
        connectionTypes: List<String>,
        page: Int,
        size: Int,
        eventTime: List<String>?
    ): PagedEventsDto

    fun handleGetTeamAchievements(id: Long, page: Int, size: Int): PagedAchievementsDto
    fun handleGetTeamPeople(id: Long, page: Int, size: Int, phrase: String?): PagedPeopleDto
    fun handleUpdateTeamDances(id: Long, dances: List<DanceDto>)
    fun handleSetTeamPeople(id: Long, people: List<PersonDto>)
}