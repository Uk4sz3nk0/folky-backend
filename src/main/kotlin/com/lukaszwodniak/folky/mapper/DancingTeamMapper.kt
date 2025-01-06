package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDataDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamListElementDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Named
import org.mapstruct.factory.Mappers
import java.io.File
import java.nio.file.Files
import java.util.*

/**
 * DancingTeamMapper
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Mapper(componentModel = "spring")
interface DancingTeamMapper {

    @Mapping(source = "dancingTeam", target = "dancersCount", qualifiedByName = ["dancersCount"])
    @Mapping(source = "dancingTeam", target = "musiciansCount", qualifiedByName = ["musiciansCount"])
    fun map(dancingTeam: DancingTeam): DancingTeamDto
    fun map(dancingTeamDto: DancingTeamDto): DancingTeam
    fun map(dancingTeams: List<DancingTeam>): MutableList<DancingTeamDto>
    fun mapToListElements(dancingTeams: List<DancingTeam>): MutableList<DancingTeamListElementDto>

    @Mapping(source = "dancingTeam", target = "logo", qualifiedByName = ["mapLogo"])
    fun mapToListElement(dancingTeam: DancingTeam): DancingTeamListElementDto
    fun mapDancingTeamData(dancingTeam: DancingTeam): DancingTeamDataDto

    companion object {
        val INSTANCE: DancingTeamMapper = Mappers.getMapper(DancingTeamMapper::class.java)

        @Named("dancersCount")
        @JvmStatic
        fun dancersCount(dancingTeam: DancingTeam): Int = dancingTeam.dancers?.size ?: 0

        @Named("musiciansCount")
        @JvmStatic
        fun musiciansCount(dancingTeam: DancingTeam): Int = dancingTeam.musicians?.size ?: 0

        @Named("mapLogo")
        @JvmStatic
        fun mapLogo(dancingTeam: DancingTeam): String {
            val filesDir = File("storage/${dancingTeam.filesUUID}")
            if (filesDir.exists()) {
                if (dancingTeam.logoFilename?.isNotEmpty() == true) {
                    val logoFile = File("storage/${dancingTeam.filesUUID}/${dancingTeam.logoFilename}")
                    val imageBytes = Files.readAllBytes(logoFile.toPath())
                    return Base64.getEncoder().encodeToString(imageBytes)
                }
            }
            return ""
        }
    }
}