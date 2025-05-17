package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.AchievementsHandler
import com.lukaszwodniak.folky.rest.achievements.specification.api.AchievementsApi
import com.lukaszwodniak.folky.rest.achievements.specification.models.AchievementDto
import com.lukaszwodniak.folky.rest.achievements.specification.models.PagedAchievementsDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * AchievementsController
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@RestController
class AchievementsController(
    private val achievementsHandler: AchievementsHandler,
) : AchievementsApi {

    @EndpointLogger
    override fun addAchievement(achievement: AchievementDto?): ResponseEntity<Void> {
        achievement?.let { achievementsHandler.handleAddAchievement(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deleteAchievement(id: Long?): ResponseEntity<Void> {
        id?.let { achievementsHandler.handleDeleteAchievement(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getAchievement(id: Long?): ResponseEntity<AchievementDto> {
        val achievement = id?.let { achievementsHandler.handleGetAchievement(it) }
        return ResponseEntity.ok(achievement)
    }

    @EndpointLogger
    override fun getAchievements(page: Int?, size: Int?, phrase: String?): ResponseEntity<PagedAchievementsDto> {
        val achievements =
            achievementsHandler.handleGetAchievements(page ?: DEFAULT_PAGE_NUMBER, size ?: DEFAULT_PAGE_SIZE)
        return ResponseEntity.ok(achievements)
    }

    @EndpointLogger
    override fun updateAchievement(id: Long?, achievement: AchievementDto?): ResponseEntity<Void> {
        achievement?.let { a ->
            id?.let {
                achievementsHandler.handleUpdateAchievement(it, a)
            }
        }
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val DEFAULT_PAGE_NUMBER: Int = 0
        private const val DEFAULT_PAGE_SIZE: Int = 10
    }
}