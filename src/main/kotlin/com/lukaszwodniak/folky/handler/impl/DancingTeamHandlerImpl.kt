package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.enums.EventConnectionType
import com.lukaszwodniak.folky.enums.EventTime
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.handler.DancingTeamHandler
import com.lukaszwodniak.folky.mapper.DanceMapper
import com.lukaszwodniak.folky.mapper.DancingTeamMapper
import com.lukaszwodniak.folky.mapper.FilterMapper
import com.lukaszwodniak.folky.mapper.PeopleMapper
import com.lukaszwodniak.folky.records.Pagination
import com.lukaszwodniak.folky.records.SortObject
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.SubscriptionRepository
import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto
import com.lukaszwodniak.folky.rest.specification.models.*
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.events.EventsService
import com.lukaszwodniak.folky.service.files.FilesService
import com.lukaszwodniak.folky.service.users.UserService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * DancingTeamHandler
 *
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Service
@RequiredArgsConstructor
class DancingTeamHandlerImpl(
    private val dancingTeamService: DancingTeamService,
    private val userService: UserService,
    private val dancingTeamRepository: DancingTeamRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val filesService: FilesService,
    private val eventsService: EventsService
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

    override fun handleGetTeamDances(teamId: Long, page: Int, size: Int): PagedDancesDto {
        val dances = dancingTeamService.getTeamDances(teamId, PageRequest.of(page, size))
        return DanceMapper.INSTANCE.mapPagedToDto(dances)
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
        return DancingTeamMapper.INSTANCE.mapListElementsToPage(pagedTeams, filesService)
    }

    override fun handleGetSubscribedTeams(id: Long, page: Int, size: Int): PageDancingTeamListElementDto {
        val user = userService.getUserById(id)
        val pageRequest = PageRequest.of(page, size)
        user?.let {
            return DancingTeamMapper.INSTANCE.mapListElementsToPage(
                dancingTeamService.getSubscribedTeams(
                    user,
                    pageRequest
                ),
                filesService
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
        return filesService.getGalleryUrls(dancingTeam)
    }

    override fun handleGetEvents(
        id: Long,
        connectionTypes: List<String>,
        page: Int,
        size: Int,
        eventTime: List<String>?
    ): PagedEventsDto {
        val team = dancingTeamRepository.findById(id).orElseThrow { NoSuchDancingTeamException(id) }
        val mappedConnectionTypes = connectionTypes.map { EventConnectionType.valueOf(it) }
        val mappedEventTime = eventTime?.map { EventTime.valueOf(it) } ?: emptyList()
        val events = eventsService.getTeamEvents(team, page, size, mappedConnectionTypes, mappedEventTime)

        return DancingTeamMapper.INSTANCE.mapToPagedEvents(events)
    }

    override fun handleGetTeamAchievements(id: Long, page: Int, size: Int): PagedAchievementsDto {
        val achievements = dancingTeamService.getTeamAchievements(id, PageRequest.of(page, size))
        return DancingTeamMapper.INSTANCE.mapAchievementsToDto(achievements)
    }

    override fun handleGetTeamPeople(id: Long, page: Int, size: Int, phrase: String?): PagedPeopleDto {
        val people = dancingTeamService.getTeamPeople(id, PageRequest.of(page, size), phrase)
        return PeopleMapper.INSTANCE.mapToPageable(people)
    }

    override fun handleUpdateTeamDances(id: Long, dances: List<DanceDto>) {
        val mappedDances = DanceMapper.INSTANCE.mapDancesFromDto(dances)
        dancingTeamService.updateTeamDances(teamId = id, mappedDances)
    }

    override fun handleSetTeamPeople(id: Long, people: List<PersonDto>) {
        val mappedPeople = PeopleMapper.INSTANCE.mapFromDtoList(people)
        dancingTeamService.setTeamPeople(id, mappedPeople)
    }
}