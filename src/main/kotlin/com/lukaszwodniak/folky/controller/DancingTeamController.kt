package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.rest.dancing_team.specification.api.DancingTeamApi
import com.lukaszwodniak.folky.rest.specification.models.*
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
    override fun getTeams(
        phrase: String?,
        page: Int?,
        size: Int?,
    ): ResponseEntity<PageDancingTeamListElementDto> {
        val teams = dancingTeamHandler.handleGetTeams(page ?: DEFAULT_PAGE_NUMBER, size ?: DEFAULT_PAGE_SIZE, phrase)
        return ResponseEntity.ok(teams)
    }

    @EndpointLogger
    override fun updateTeam(dancingTeam: DancingTeamDto?): ResponseEntity<DancingTeamDto> {
        return ResponseEntity.ok(dancingTeam?.let { dancingTeamHandler.handleUpdateTeam(it) })
    }

    @EndpointLogger
    override fun addSubscription(dancingTeamId: Long?): ResponseEntity<Void> {
        dancingTeamId?.let { dancingTeamHandler.handleAddSubscription(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deleteSubscription(dancingTeamId: Long?): ResponseEntity<Void> {
        dancingTeamId?.let { dancingTeamHandler.handleDeleteSubscription(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getSubscribers(teamId: Long?): ResponseEntity<MutableList<UserDto>> {
        TODO("Not yet implemented")
    }

    @EndpointLogger
    override fun getGalleryImages(id: Long?): ResponseEntity<MutableList<String>> {
        val images = id?.let { dancingTeamHandler.handleGetGalleryImages(it) }
        return ResponseEntity.ok(images)
    }

    companion object {
        private const val DEFAULT_PAGE_NUMBER: Int = 0
        private const val DEFAULT_PAGE_SIZE: Int = 10
    }
}