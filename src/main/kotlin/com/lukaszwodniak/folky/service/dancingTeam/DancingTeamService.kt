package com.lukaszwodniak.folky.service.dancingTeam

import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.records.DancingTeamFiles
import com.lukaszwodniak.folky.records.FilterTeamsObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * DancingTeamService
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Service
interface DancingTeamService {

    fun addTeam(team: DancingTeam, user: User?): DancingTeam
    fun addTeam(team: DancingTeam, files: DancingTeamFiles): DancingTeam
    fun updateTeam(team: DancingTeam): DancingTeam
    fun deleteTeam(teamId: Long)
    fun getById(teamId: Long): DancingTeam
    fun getByRegion(region: Region): List<DancingTeam>
    fun getTeamDances(teamId: Long): List<Dance>
    fun getTeamDancers(teamId: Long): List<User>
    fun getTeamMusicians(teamId: Long): List<User>
    fun getTeams(pageRequest: PageRequest, searchPhrase: String?): Page<DancingTeam>
    fun getTeams(pageRequest: PageRequest, searchPhrase: String?, filterTeamsObject: FilterTeamsObject?): Page<DancingTeam>
    fun getTeamsByName(phrase: String): List<DancingTeam>
    fun getSubscribedTeams(user: User): List<DancingTeam>
    fun getSubscribedTeams(user: User, pageRequest: PageRequest): Page<DancingTeam>
    fun addSubscription(team: DancingTeam, user: User)
    fun deleteSubscription(team: DancingTeam, user: User)
}