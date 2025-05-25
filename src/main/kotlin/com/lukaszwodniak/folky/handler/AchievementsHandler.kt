package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.achievements.specification.models.AchievementDto
import com.lukaszwodniak.folky.rest.achievements.specification.models.PagedAchievementsDto

/**
 * AchievementsHandler
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

interface AchievementsHandler {

    fun handleAddAchievement(achievement: AchievementDto)
    fun handleDeleteAchievement(id: Long)
    fun handleGetAchievement(id: Long): AchievementDto
    fun handleGetAchievements(page: Int, size: Int): PagedAchievementsDto
    fun handleUpdateAchievement(id: Long, achievement: AchievementDto)
}