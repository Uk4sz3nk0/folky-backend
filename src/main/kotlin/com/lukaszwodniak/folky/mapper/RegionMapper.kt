package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.rest.specification.models.RegionDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * RegionMapper
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

@Mapper
interface RegionMapper {

    fun map(region: RegionDto): Region
    fun map(region: Region): RegionDto
    fun map(regions: List<Region>): MutableList<RegionDto>

    companion object {
        val INSTANCE: RegionMapper = Mappers.getMapper(RegionMapper::class.java)
    }
}