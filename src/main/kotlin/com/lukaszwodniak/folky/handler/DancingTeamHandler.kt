package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.records.Pagination
import com.lukaszwodniak.folky.records.SortObject
import com.lukaszwodniak.folky.rest.specification.models.*

/**
 * DancingTeamHandler
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

interface DancingTeamHandler {

    fun handleAddTeam(team: DancingTeamDto): DancingTeamDto
    fun handleUpdateTeam(team: DancingTeamDto): DancingTeamDto
    fun handleDeleteTeam(teamId: Long)
    fun handleGetById(teamId: Long): DancingTeamDto
    fun handleGetByRegion(regionId: Long): MutableList<DancingTeamDto>
    fun handleGetTeamDances(teamId: Long): MutableList<DanceDto>
    fun handleGetTeamDancers(teamId: Long): MutableList<UserDto>
    fun handleGetTeamMusicians(teamId: Long): MutableList<UserDto>
    fun handleGetTeams(
        pagination: Pagination,
        sortObject: SortObject,
        searchPhrase: String?
    ): PageDancingTeamListElementDto

    fun handleGetTeams(
        pagination: Pagination,
        sortObject: SortObject,
        searchPhrase: String?,
        filterObject: FilterObjectDto?
    ): PageDancingTeamListElementDto

    fun handleGetTeamsByName(phrase: String): MutableList<DancingTeamDto>
    fun handleGetSubscribedTeams(): MutableList<DancingTeamListElementDto>
    fun handleGetSubscribedTeams(id: Long, page: Int, size: Int): PageDancingTeamListElementDto
    fun handleAddSubscription(teamId: Long)
    fun handleDeleteSubscription(teamId: Long)
    fun handleGetGalleryImages(id: Long): MutableList<String>
}