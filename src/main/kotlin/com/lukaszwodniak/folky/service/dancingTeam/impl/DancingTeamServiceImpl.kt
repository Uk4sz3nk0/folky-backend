package com.lukaszwodniak.folky.service.dancingTeam.impl

import com.lukaszwodniak.folky.error.DancingTeamWithGivenNameExistsException
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.model.*
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.SubscriptionRepository
import com.lukaszwodniak.folky.repository.UserRepository
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

/**
 * DancingTeamServiceImpl
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Service
@RequiredArgsConstructor
class DancingTeamServiceImpl(
    private val dancingTeamRepository: DancingTeamRepository,
    private val userRepository: UserRepository,
    private val subscriptionRepository: SubscriptionRepository
) : DancingTeamService {

    override fun addTeam(team: DancingTeam): DancingTeam {
        // TODO: Implement additional logic if needed
        if (dancingTeamRepository.existsByNameIgnoreCase(team.name)) {
            throw DancingTeamWithGivenNameExistsException("Dancing team with name \"${team.name}\" already exists")
        }
        return dancingTeamRepository.saveAndFlush(team)
    }

    override fun updateTeam(team: DancingTeam): DancingTeam {
        val existingTeam =
            dancingTeamRepository.findById(team.id ?: -1).orElseThrow { NoSuchDancingTeamException(team.id ?: -1) }
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
        return dancingTeam.dances ?: emptyList()
    }

    override fun getTeamDancers(teamId: Long): List<User> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.dancers ?: emptyList()
    }

    override fun getTeamMusicians(teamId: Long): List<User> {
        val dancingTeam =
            dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return dancingTeam.musicians ?: emptyList()
    }

    override fun getTeams(): List<DancingTeam> {
        return dancingTeamRepository.findAll()
    }

    override fun getTeamsByName(phrase: String): List<DancingTeam> {
        return dancingTeamRepository.findAllByNameContainsIgnoreCase(phrase).orElse(emptyList())
    }

    override fun getSubscribedTeams(user: User): List<DancingTeam> {
        return subscriptionRepository.findAllByUser(user).map { sub -> sub.dancingTeam }
    }

    override fun addSubscription(team: DancingTeam, user: User) {
        if (!subscriptionRepository.existsByUserAndDancingTeam(user, team)) {
            val newSubscription = Subscription(user = user, dancingTeam = team)
            subscriptionRepository.saveAndFlush(newSubscription)
        }
    }

    override fun deleteSubscription(team: DancingTeam, user: User) {
        if (subscriptionRepository.existsByUserAndDancingTeam(user, team)) {
            val optionalSub = subscriptionRepository.findByUserAndDancingTeam(user, team)
            optionalSub.ifPresent { subscriptionRepository.deleteById(it.id!!) }
        }
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