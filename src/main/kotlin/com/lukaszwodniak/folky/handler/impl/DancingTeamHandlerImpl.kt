package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.mapper.DanceMapper
import com.lukaszwodniak.folky.mapper.DancingTeamMapper
import com.lukaszwodniak.folky.mapper.UserMapper
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.RegionRepository
import com.lukaszwodniak.folky.repository.SubscriptionRepository
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamListElementDto
import com.lukaszwodniak.folky.rest.specification.models.UserDto
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.users.UserService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

/**
 * DancingTeamHandler
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Service
@RequiredArgsConstructor
class DancingTeamHandlerImpl(
    private val dancingTeamService: DancingTeamService,
    private val regionRepository: RegionRepository,
    private val userService: UserService,
    private val dancingTeamRepository: DancingTeamRepository,
    private val subscriptionRepository: SubscriptionRepository
) : DancingTeamHandler {

    override fun handleAddTeam(team: DancingTeamDto): DancingTeamDto {
        val mappedDancingTeam = DancingTeamMapper.INSTANCE.map(team)
        return DancingTeamMapper.INSTANCE.map(dancingTeamService.addTeam(mappedDancingTeam))
    }

    override fun handleUpdateTeam(team: DancingTeamDto): DancingTeamDto {
        val mappedUpdated = DancingTeamMapper.INSTANCE.map(team)
        return DancingTeamMapper.INSTANCE.map(dancingTeamService.updateTeam(mappedUpdated))
    }

    override fun handleDeleteTeam(teamId: Long) {
        dancingTeamService.deleteTeam(teamId)
    }

    override fun handleGetById(teamId: Long): DancingTeamDto {
        val dancingTeamDto = DancingTeamMapper.INSTANCE.map(dancingTeamService.getById(teamId))
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val user = userService.getUserFromContext()
        val isTeamSubscribed = user?.let {
            subscriptionRepository.existsByUserAndDancingTeam(user, dancingTeam)
        } ?: false
        return dancingTeamDto.isSubscribed(isTeamSubscribed)
    }

    override fun handleGetByRegion(regionId: Long): MutableList<DancingTeamDto> {
        val region = regionRepository.findById(regionId).orElseThrow()
        return DancingTeamMapper.INSTANCE.map(dancingTeamService.getByRegion(region))
    }

    override fun handleGetTeamDances(teamId: Long): MutableList<DanceDto> {
        return DanceMapper.INSTANCE.map(dancingTeamService.getTeamDances(teamId))
    }

    override fun handleGetTeamDancers(teamId: Long): MutableList<UserDto> {
        return UserMapper.INSTANCE.map(dancingTeamService.getTeamDancers(teamId))
    }

    override fun handleGetTeamMusicians(teamId: Long): MutableList<UserDto> {
        return UserMapper.INSTANCE.map(dancingTeamService.getTeamMusicians(teamId))
    }

    override fun handleGetTeams(): MutableList<DancingTeamListElementDto> {
        return DancingTeamMapper.INSTANCE.mapToListElements(dancingTeamService.getTeams())
    }

    override fun handleGetTeamsByName(phrase: String): MutableList<DancingTeamDto> {
        return DancingTeamMapper.INSTANCE.map(dancingTeamService.getTeamsByName(phrase))
    }

    override fun handleGetSubscribedTeams(): MutableList<DancingTeamListElementDto> {
        val user = userService.getUserFromContext()
        user?.let {
            return DancingTeamMapper.INSTANCE.mapToListElements(dancingTeamService.getSubscribedTeams(user))
        } ?: return mutableListOf()
    }

    override fun handleAddSubscription(teamId: Long) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val user = userService.getUserFromContext()
        user?.let {
            dancingTeamService.addSubscription(dancingTeam, it)
        }
    }

    override fun handleDeleteSubscription(teamId: Long) {
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val user = userService.getUserFromContext()
        user?.let {
            dancingTeamService.deleteSubscription(dancingTeam, it)
        }
    }
}