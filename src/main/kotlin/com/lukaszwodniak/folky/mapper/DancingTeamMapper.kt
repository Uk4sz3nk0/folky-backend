package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Achievement
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Event
import com.lukaszwodniak.folky.rest.specification.models.*
import com.lukaszwodniak.folky.service.files.FilesService
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * DancingTeamMapper
 *
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Mapper(componentModel = "spring")
interface DancingTeamMapper {

    @Mapping(source = "recruitmentOpened", target = "isRecruitmentOpened")
    @Mapping(source = "verified", target = "isVerified")
    fun map(dancingTeam: DancingTeam): DancingTeamDto

    @Mapping(target = "director", ignore = true)
    @Mapping(target = "accountUser", ignore = true)
    fun map(dancingTeamDto: DancingTeamDto): DancingTeam
    fun map(dancingTeams: List<DancingTeam>): MutableList<DancingTeamDto>
    fun mapToListElements(
        dancingTeams: List<DancingTeam>,
        @Context filesService: FilesService
    ): MutableList<DancingTeamListElementDto>

    fun mapListElementsToPage(
        pagedTeams: Page<DancingTeam>,
        @Context filesService: FilesService
    ): PageDancingTeamListElementDto

    @Mapping(target = "isVerified", source = "verified")
    fun mapToListElement(dancingTeam: DancingTeam, @Context filesService: FilesService): DancingTeamListElementDto
    fun mapDancingTeamData(dancingTeam: DancingTeam): DancingTeamDataDto

    fun mapEventToDto(event: Event): EventDto
    fun mapEventListToDto(events: List<Event>): List<EventDto>

    fun mapToPagedEvents(events: Page<Event>): PagedEventsDto

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "dancingTeam.id", target = "dancingTeamId")
    fun mapAchievementToDto(achievement: Achievement): AchievementDto
    fun mapAchievementsToDto(achievements: Page<Achievement>): PagedAchievementsDto

    companion object {
        val INSTANCE: DancingTeamMapper = Mappers.getMapper(DancingTeamMapper::class.java)
    }
}