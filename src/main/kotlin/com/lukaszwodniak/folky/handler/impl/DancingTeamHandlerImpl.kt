package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.mapper.DanceMapper
import com.lukaszwodniak.folky.mapper.DancingTeamMapper
import com.lukaszwodniak.folky.mapper.FilterMapper
import com.lukaszwodniak.folky.mapper.UserMapper
import com.lukaszwodniak.folky.records.Pagination
import com.lukaszwodniak.folky.records.SortObject
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.RegionRepository
import com.lukaszwodniak.folky.repository.SubscriptionRepository
import com.lukaszwodniak.folky.rest.specification.models.*
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.users.UserService
import com.lukaszwodniak.folky.utils.FileUtils
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

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

    @Transactional
    override fun handleAddTeam(team: DancingTeamDto): DancingTeamDto {
        team.filesUUID(UUID.randomUUID())
        val mappedDancingTeam = DancingTeamMapper.INSTANCE.map(team)
        val user = userService.getUserFromContext()
        return DancingTeamMapper.INSTANCE.map(dancingTeamService.addTeam(mappedDancingTeam, user))
    }

    override fun handleUpdateTeam(team: DancingTeamDto): DancingTeamDto {
        val existingTeam = dancingTeamRepository.findById(team.id).orElseThrow { NoSuchDancingTeamException(team.id) }
        team.filesUUID(existingTeam.filesUUID)
        val mappedUpdated = DancingTeamMapper.INSTANCE.map(team)
        mappedUpdated.director = userService.getUserById(team.directorId)
        mappedUpdated.accountUser = userService.getUserById(team.accountUserId)
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

    override fun handleGetTeams(
        pagination: Pagination,
        sortObject: SortObject,
        searchPhrase: String?
    ): PageDancingTeamListElementDto {
        val pageRequest = PageRequest.of(pagination.page, pagination.size, Sort.by(sortObject.direction, sortObject.orderBy))
        val pagedTeams = dancingTeamService.getTeams(pageRequest, searchPhrase)
        return DancingTeamMapper.INSTANCE.mapListElementsToPage(pagedTeams)
    }

    override fun handleGetTeams(
        pagination: Pagination,
        sortObject: SortObject,
        searchPhrase: String?,
        filterObject: FilterObjectDto?
    ): PageDancingTeamListElementDto {
        val pageRequest =
            PageRequest.of(pagination.page, pagination.size, Sort.by(sortObject.direction, sortObject.orderBy))
        val mappedFilterObject = filterObject?.let { FilterMapper.mapFilterObject(it) }
        val pagedTeams = dancingTeamService.getTeams(pageRequest, searchPhrase, mappedFilterObject)
        return DancingTeamMapper.INSTANCE.mapListElementsToPage(pagedTeams)
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

    override fun handleGetSubscribedTeams(id: Long, page: Int, size: Int): PageDancingTeamListElementDto {
        val user = userService.getUserById(id)
        val pageRequest = PageRequest.of(page, size)
        user?.let {
            return DancingTeamMapper.INSTANCE.mapListElementsToPage(
                dancingTeamService.getSubscribedTeams(
                    user,
                    pageRequest
                )
            )
        } ?: return PageDancingTeamListElementDto()
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

    override fun handleGetGalleryImages(id: Long): MutableList<String> {
        val dancingTeam = dancingTeamRepository.findById(id).orElseThrow { NoSuchDancingTeamException(id) }
        return FileUtils.getGalleryUrls(dancingTeam)
    }
}