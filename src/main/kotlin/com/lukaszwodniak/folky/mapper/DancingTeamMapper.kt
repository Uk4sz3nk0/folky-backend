package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDataDto
import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * DancingTeamMapper
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

@Mapper
interface DancingTeamMapper {

    fun map(dancingTeam: DancingTeam): DancingTeamDto
    fun map(dancingTeamDto: DancingTeamDto): DancingTeam
    fun map(dancingTeams: List<DancingTeam>): MutableList<DancingTeamDto>
    fun mapDancingTeamData(dancingTeam: DancingTeam): DancingTeamDataDto

    companion object {
        val INSTANCE: DancingTeamMapper = Mappers.getMapper(DancingTeamMapper::class.java)
    }
}