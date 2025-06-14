package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Achievement
import com.lukaszwodniak.folky.rest.achievements.specification.models.AchievementDto
import com.lukaszwodniak.folky.rest.achievements.specification.models.PagedAchievementsDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * AchievementMapper
 *
 * Created on: 2025-05-18
 * @author Łukasz Wodniak
 */

@Mapper
interface AchievementMapper {

    @Mapping(target = "dancingTeam", ignore = true)
    @Mapping(target = "event", ignore = true)
    fun mapAchievementFromDto(achievement: AchievementDto): Achievement

    @Mapping(source = "dancingTeam.id", target = "dancingTeamId")
    @Mapping(source = "event.id", target = "eventId")
    fun mapAchievementToDto(achievement: Achievement): AchievementDto

    fun mapAchievementsToDto(achievements: Page<Achievement>): PagedAchievementsDto
    fun mapAchievements(achievements: List<Achievement>): List<AchievementDto>

    companion object {
        val INSTANCE: AchievementMapper = Mappers.getMapper(AchievementMapper::class.java)
    }
}