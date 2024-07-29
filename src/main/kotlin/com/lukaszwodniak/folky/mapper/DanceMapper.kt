package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * DanceMapper
 * Created on: 2024-08-12
 * @author Łukasz Wodniak
 */

@Mapper
interface DanceMapper {

    fun map(dance: Dance): DanceDto

    fun map(dances: List<Dance>): MutableList<DanceDto>

    companion object {
        val INSTANCE: DanceMapper = Mappers.getMapper(DanceMapper::class.java)
    }
}