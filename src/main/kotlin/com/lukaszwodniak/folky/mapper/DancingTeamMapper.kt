package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.SocialMedia
import com.lukaszwodniak.folky.records.FilterTeamsObject
import com.lukaszwodniak.folky.records.Range
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDataDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamListElementDto
import com.lukaszwodniak.folky.rest.specification.models.FilterObjectDto
import com.lukaszwodniak.folky.rest.specification.models.PageDancingTeamListElementDto
import com.lukaszwodniak.folky.rest.specification.models.RangeDto
import com.lukaszwodniak.folky.service.files.FilesService
import org.mapstruct.Context
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.file.Files
import java.util.*

/**
 * DancingTeamMapper
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Mapper(
    componentModel = "spring",
//    uses = [SocialMedia::class]
)
interface DancingTeamMapper {

    fun map(dancingTeam: DancingTeam): DancingTeamDto
    @Mapping(target = "director", ignore = true)
    @Mapping(target = "accountUser", ignore = true)
    fun map(dancingTeamDto: DancingTeamDto): DancingTeam
    fun map(dancingTeams: List<DancingTeam>): MutableList<DancingTeamDto>
    fun mapToListElements(dancingTeams: List<DancingTeam>, @Context filesService: FilesService): MutableList<DancingTeamListElementDto>

    fun mapListElementsToPage(pagedTeams: Page<DancingTeam>, @Context filesService: FilesService): PageDancingTeamListElementDto

    @Mapping(source = "dancingTeam", target = "logo", qualifiedByName = ["mapLogo"])
    fun mapToListElement(dancingTeam: DancingTeam, @Context filesService: FilesService): DancingTeamListElementDto
    fun mapDancingTeamData(dancingTeam: DancingTeam): DancingTeamDataDto

    companion object {
        val INSTANCE: DancingTeamMapper = Mappers.getMapper(DancingTeamMapper::class.java)

        @Named("dancersCount")
        @JvmStatic
        fun dancersCount(dancingTeam: DancingTeam): Int = dancingTeam.dancers?.size ?: 0

        @Named("musiciansCount")
        @JvmStatic
        fun musiciansCount(dancingTeam: DancingTeam): Int = 0

        @Named("mapLogo")
        @JvmStatic
        fun mapLogo(dancingTeam: DancingTeam, @Context filesService: FilesService): String {
            val file = dancingTeam.logoFilename?.let { filesService.getImageFile(dancingTeam.filesUUID, it) }
            return file?.inputStream?.use { Base64.getEncoder().encodeToString(it.readBytes()) } ?: ""
        }
    }
}