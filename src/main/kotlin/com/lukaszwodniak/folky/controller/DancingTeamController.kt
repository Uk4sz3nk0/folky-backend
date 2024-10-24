package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.rest.dancing_team.specification.api.DancingTeamApi
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import com.lukaszwodniak.folky.rest.specification.models.UserDto
import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * DancingTeamController
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Slf4j
@RestController
class DancingTeamController(private val dancingTeamHandler: DancingTeamHandler) : DancingTeamApi {

    @EndpointLogger
    override fun addDancingTeam(dancingTeam: DancingTeamDto?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(dancingTeam?.let { dancingTeamHandler.handleAddTeam(it) })
    }

    @EndpointLogger
    override fun deleteTeam(teamId: Long?): ResponseEntity<Void> {
        teamId?.let { dancingTeamHandler.handleDeleteTeam(it) }
        return ResponseEntity.ok(null)
    }

    @EndpointLogger
    override fun getById(teamId: Long?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetById(it) })
    }

    @EndpointLogger
    override fun getByName(phrase: String?): ResponseEntity<MutableList<DancingTeamDto>> {
        return ResponseEntity.ok(phrase?.let { dancingTeamHandler.handleGetTeamsByName(it) })
    }

    @EndpointLogger
    override fun getByRegion(regionId: Long?): ResponseEntity<MutableList<DancingTeamDto>> {
        return ResponseEntity.ok(regionId?.let { dancingTeamHandler.handleGetByRegion(it) })
    }

    @EndpointLogger
    override fun getTeamDancers(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetTeamDancers(it) })
    }

    @EndpointLogger
    override fun getTeamDances(teamId: Long?): ResponseEntity<MutableList<DanceDto>> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetTeamDances(it) })
    }

    @EndpointLogger
    override fun getTeamMusicians(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        return ResponseEntity.ok(teamId?.let { dancingTeamHandler.handleGetTeamMusicians(it) })
    }

    @EndpointLogger
    override fun getTeams(): ResponseEntity<MutableList<DancingTeamDto>> {
        return ResponseEntity.ok(dancingTeamHandler.handleGetTeams())
    }

    @EndpointLogger
    override fun updateTeam(dancingTeam: DancingTeamDto?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(dancingTeam?.let { dancingTeamHandler.handleUpdateTeam(it) })
    }

    override fun getSubscribers(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        TODO("Not yet implemented")
    }
}