package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamListElementDto
import com.lukaszwodniak.folky.rest.specification.models.UserDto

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
    fun handleGetTeams(): MutableList<DancingTeamListElementDto>
    fun handleGetTeamsByName(phrase: String): MutableList<DancingTeamDto>
    fun handleGetSubscribedTeams(): MutableList<DancingTeamListElementDto>
    fun handleAddSubscription(teamId: Long)
    fun handleDeleteSubscription(teamId: Long)
}