package com.lukaszwodniak.folky.service.dancingTeam

import com.lukaszwodniak.folky.model.*
import com.lukaszwodniak.folky.records.DancingTeamFiles
import com.lukaszwodniak.folky.records.FilterTeamsObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * DancingTeamService
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

interface DancingTeamService {

    fun addTeam(team: DancingTeam, user: User?): DancingTeam
    fun addTeam(team: DancingTeam, files: DancingTeamFiles): DancingTeam
    fun updateTeam(team: DancingTeam): DancingTeam
    fun deleteTeam(teamId: Long)
    fun getById(teamId: Long): DancingTeam
    fun getByRegion(region: Region): List<DancingTeam>
    fun getTeamDances(teamId: Long, pageRequest: PageRequest): Page<Dance>
    fun getTeamDancers(teamId: Long): List<User>
    fun getTeamMusicians(teamId: Long): List<User>
    fun getTeams(pageRequest: PageRequest, searchPhrase: String?): Page<DancingTeam>
    fun getTeams(
        pageRequest: PageRequest,
        searchPhrase: String?,
        filterTeamsObject: FilterTeamsObject?
    ): Page<DancingTeam>

    fun getTeamsByName(phrase: String): List<DancingTeam>
    fun getSubscribedTeams(user: User): List<DancingTeam>
    fun getSubscribedTeams(user: User, pageRequest: PageRequest): Page<DancingTeam>
    fun addSubscription(team: DancingTeam, user: User)
    fun deleteSubscription(team: DancingTeam, user: User)
    fun getTeamAchievements(teamId: Long, pageRequest: PageRequest): Page<Achievement>
    fun getTeamPeople(teamId: Long, pageRequest: PageRequest, phrase: String?): Page<Person>
    fun updateTeamDances(teamId: Long, dances: List<Dance>)
    fun removeDanceFromTeam(teamId: Long, danceId: Long)
    fun setTeamPeople(teamId: Long, people: List<Person>)
}