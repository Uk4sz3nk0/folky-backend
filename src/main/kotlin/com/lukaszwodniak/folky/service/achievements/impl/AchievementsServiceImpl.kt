package com.lukaszwodniak.folky.service.achievements.impl

import com.lukaszwodniak.folky.model.Achievement
import com.lukaszwodniak.folky.repository.AchievementsRepository
import com.lukaszwodniak.folky.service.achievements.AchievementsService
import com.lukaszwodniak.folky.service.people.PeopleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * AchievementsServiceImpl
 *
 * Created on: 2025-05-18
 * @author Łukasz Wodniak
 */

@Service
class AchievementsServiceImpl(
    private val achievementsRepository: AchievementsRepository,
    private val peopleService: PeopleService
) : AchievementsService {

    @Transactional
    override fun addAchievement(achievement: Achievement) {
        val updatedPeople =
            peopleService.updatedPeople(achievement.distinguishedPeople, achievement.dancingTeam).toMutableList()

        achievementsRepository.saveAndFlush(achievement.copy(distinguishedPeople = updatedPeople))
    }

    override fun deleteAchievement(achievement: Achievement) {
        achievementsRepository.delete(achievement)
    }

    override fun getAchievement(id: Long): Achievement {
        return achievementsRepository.findById(id).orElseThrow { NoSuchElementException("No achievement with id $id") }
    }

    override fun getAchievements(pageRequest: PageRequest): Page<Achievement> {
        return achievementsRepository.findAll(pageRequest.withSort(Sort.Direction.DESC, "id"))
    }

    override fun updateAchievement(existingAchievement: Achievement, updateData: Achievement) {
        val updatedPeople =
            peopleService.updatedPeople(updateData.distinguishedPeople, updateData.dancingTeam).toMutableList()

        val achievementToUpdate = existingAchievement.copy(
            name = updateData.name,
            description = updateData.description,
            date = updateData.date,
            year = updateData.year,
            event = updateData.event,
            city = updateData.city,
            organizer = updateData.organizer,
            category = updateData.category,
            level = updateData.level,
            distinguishedPeople = updatedPeople,
            dancingTeam = updateData.dancingTeam,
        )
        achievementsRepository.save(achievementToUpdate)
    }

}