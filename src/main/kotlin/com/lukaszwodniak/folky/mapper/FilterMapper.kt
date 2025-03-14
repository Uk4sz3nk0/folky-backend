package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.records.FilterTeamsObject
import com.lukaszwodniak.folky.records.Range
import com.lukaszwodniak.folky.rest.specification.models.FilterObjectDto
import com.lukaszwodniak.folky.rest.specification.models.RangeDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * FilterMapper
 *
 * Created on: 2025-03-18
 * @author Łukasz Wodniak
 */

@Mapper
interface FilterMapper {

    fun mapRange(range: RangeDto): Range

    companion object {
        val INSTANCE: FilterMapper = Mappers.getMapper(FilterMapper::class.java)

        fun mapFilterObject(filterObjectDto: FilterObjectDto): FilterTeamsObject {
            return FilterTeamsObject(
                INSTANCE.mapRange(filterObjectDto.creationDate),
                INSTANCE.mapRange(filterObjectDto.dancersAmount),
                INSTANCE.mapRange(filterObjectDto.musiciansAmount),
                filterObjectDto.selectedRegions,
                filterObjectDto.ownedSocialMedia
            )
        }
    }
}