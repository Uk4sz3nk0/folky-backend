package com.lukaszwodniak.folky.service.dancingTeam.impl

import com.lukaszwodniak.folky.error.DancingTeamWithGivenNameExistsException
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

/**
 * DancingTeamServiceImpl
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Slf4j
@Service
@RequiredArgsConstructor
class DancingTeamServiceImpl(
    private val dancingTeamRepository: DancingTeamRepository
) : DancingTeamService {

    override fun addTeam(team: DancingTeam): DancingTeam {
        // TODO: Implement additional logic if needed
        if (dancingTeamRepository.existsByNameIgnoreCase(team.name)) {
            throw DancingTeamWithGivenNameExistsException("Dancing team with name \"${team.name}\" already exists")
        }
        return dancingTeamRepository.save(team)
    }

    override fun updateTeam(team: DancingTeam): DancingTeam {
        val existingTeam =
            dancingTeamRepository.findById(team.id).orElseThrow { NoSuchDancingTeamException(team.id) }
        updateExistingDancingTeam(existingTeam, team)
        return dancingTeamRepository.save(existingTeam)
    }

    override fun deleteTeam(teamId: Long) {
        dancingTeamRepository.deleteById(teamId)
    }

    override fun getById(teamId: Long): DancingTeam {
        return dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
    }

    override fun getByRegion(region: Region): List<DancingTeam> {
        return dancingTeamRepository.findAllByRegion(region).orElse(emptyList())
    }

    override fun getTeamDances(teamId: Long): List<Dance> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.dances
    }

    override fun getTeamDancers(teamId: Long): List<User> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.dancers
    }

    override fun getTeamMusicians(teamId: Long): List<User> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.musicians
    }

    override fun getTeams(): List<DancingTeam> {
        return dancingTeamRepository.findAll()
    }

    override fun getTeamsByName(phrase: String): List<DancingTeam> {
        return dancingTeamRepository.findAllByNameContainsIgnoreCase(phrase).orElse(emptyList())
    }

    private fun updateExistingDancingTeam(existingTeam: DancingTeam, newTeamData: DancingTeam) {
        existingTeam.name = newTeamData.name
        existingTeam.description = newTeamData.description
        existingTeam.creationDate = newTeamData.creationDate
        existingTeam.director = newTeamData.director
        existingTeam.region = newTeamData.region
        existingTeam.city = newTeamData.city
        existingTeam.street = newTeamData.street
        existingTeam.homeNumber = newTeamData.homeNumber
        existingTeam.flatNumber = newTeamData.flatNumber
        existingTeam.zipCode = newTeamData.zipCode
        existingTeam.dances = newTeamData.dances
        existingTeam.dancers = newTeamData.dancers
        existingTeam.musicians = newTeamData.musicians
    }
}