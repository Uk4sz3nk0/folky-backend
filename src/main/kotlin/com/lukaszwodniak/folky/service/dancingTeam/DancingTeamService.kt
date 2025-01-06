package com.lukaszwodniak.folky.service.dancingTeam

import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.model.User

/**
 * DancingTeamService
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

interface DancingTeamService {

    fun addTeam(team: DancingTeam): DancingTeam
    fun updateTeam(team: DancingTeam): DancingTeam
    fun deleteTeam(teamId: Long)
    fun getById(teamId: Long): DancingTeam
    fun getByRegion(region: Region): List<DancingTeam>
    fun getTeamDances(teamId: Long): List<Dance>
    fun getTeamDancers(teamId: Long): List<User>
    fun getTeamMusicians(teamId: Long): List<User>
    fun getTeams(): List<DancingTeam>
    fun getTeamsByName(phrase: String): List<DancingTeam>
    fun getSubscribedTeams(user: User): List<DancingTeam>
    fun addSubscription(team: DancingTeam, user: User)
    fun deleteSubscription(team: DancingTeam, user: User)
}