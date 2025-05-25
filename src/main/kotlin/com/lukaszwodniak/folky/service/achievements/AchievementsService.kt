package com.lukaszwodniak.folky.service.achievements

import com.lukaszwodniak.folky.model.Achievement
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * AchievementsService
 *
 * Created on: 2025-05-18
 * @author Łukasz Wodniak
 */

interface AchievementsService {

    fun addAchievement(achievement: Achievement)
    fun deleteAchievement(achievement: Achievement)
    fun getAchievement(id: Long): Achievement
    fun getAchievements(pageRequest: PageRequest): Page<Achievement>
    fun updateAchievement(existingAchievement: Achievement, updateData: Achievement)
}