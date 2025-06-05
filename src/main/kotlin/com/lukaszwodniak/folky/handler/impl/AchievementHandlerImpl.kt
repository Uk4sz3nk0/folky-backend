package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.AchievementsHandler
import com.lukaszwodniak.folky.mapper.AchievementMapper
import com.lukaszwodniak.folky.model.Achievement
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Person
import com.lukaszwodniak.folky.rest.achievements.specification.models.AchievementDto
import com.lukaszwodniak.folky.rest.achievements.specification.models.PagedAchievementsDto
import com.lukaszwodniak.folky.service.achievements.AchievementsService
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.events.EventsService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * AchievementHandlerImpl
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Service
class AchievementHandlerImpl(
    private val achievementsService: AchievementsService,
    private val eventsService: EventsService,
    private val dancingTeamService: DancingTeamService
) : AchievementsHandler {

    override fun handleAddAchievement(achievement: AchievementDto) {
        val event = achievement.eventId?.let { eventsService.getEvent(it) }
        val dancingTeam = achievement.dancingTeamId?.let { dancingTeamService.getById(it) }
        val mappedAchievement = AchievementMapper.INSTANCE.mapAchievementFromDto(achievement)
        val peopleWithAssignedDancingTeam =
            mapDistinguishedPeople(mappedAchievement, dancingTeam)
        achievementsService.addAchievement(
            mappedAchievement.copy(
                event = event,
                dancingTeam = dancingTeam,
                distinguishedPeople = peopleWithAssignedDancingTeam
            )
        )
    }

    override fun handleDeleteAchievement(id: Long) {
        val achievement = achievementsService.getAchievement(id)
        achievementsService.deleteAchievement(achievement)
    }

    override fun handleGetAchievement(id: Long): AchievementDto {
        val achievement = achievementsService.getAchievement(id)
        return AchievementMapper.INSTANCE.mapAchievementToDto(achievement)
    }

    override fun handleGetAchievements(page: Int, size: Int): PagedAchievementsDto {
        val achievements = achievementsService.getAchievements(PageRequest.of(page, size))
        return AchievementMapper.INSTANCE.mapAchievementsToDto(achievements)
    }

    override fun handleUpdateAchievement(id: Long, achievement: AchievementDto) {
        val existingAchievement = achievementsService.getAchievement(id)
        val mappedAchievement = AchievementMapper.INSTANCE.mapAchievementFromDto(achievement)
        val event = achievement.eventId?.let { eventsService.getEvent(it) }
        val dancingTeam = dancingTeamService.getById(achievement.dancingTeamId)
        val mappedPeople = mapDistinguishedPeople(mappedAchievement, dancingTeam)
        achievementsService.updateAchievement(
            existingAchievement,
            mappedAchievement.copy(event = event, dancingTeam = dancingTeam, distinguishedPeople = mappedPeople)
        )
    }

    private fun mapDistinguishedPeople(
        mappedAchievement: Achievement,
        dancingTeam: DancingTeam?
    ): MutableList<Person> {
        val peopleWithAssignedDancingTeam =
            mappedAchievement.distinguishedPeople.map {
                it.copy(
                    dancingTeam = dancingTeam,
                    positions = it.positions.map { position -> position.copy(person = it) }.toMutableList()
                )
            }.toMutableList()
        return peopleWithAssignedDancingTeam
    }
}
