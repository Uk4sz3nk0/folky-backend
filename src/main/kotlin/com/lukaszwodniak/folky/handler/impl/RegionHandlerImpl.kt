package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.RegionHandler
import com.lukaszwodniak.folky.mapper.RegionMapper
import com.lukaszwodniak.folky.rest.specification.models.RegionDto
import com.lukaszwodniak.folky.service.region.RegionService
import org.springframework.stereotype.Service

/**
 * RegionHandlerImpl
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

@Service
class RegionHandlerImpl(
    val regionService: RegionService
) : RegionHandler {

    override fun handleAddRegion(region: RegionDto): RegionDto {
        val addedRegion = regionService.addRegion(RegionMapper.INSTANCE.map(region))
        return RegionMapper.INSTANCE.map(addedRegion)
    }

    override fun handleDeleteRegion(id: Long) {
        regionService.deleteRegion(id)
    }

    override fun handleGetByLocale(locale: String): MutableList<RegionDto> {
        return RegionMapper.INSTANCE.map(regionService.getByLocale(locale))
    }

    override fun handleGetByName(phrase: String): MutableList<RegionDto> {
        return RegionMapper.INSTANCE.map(regionService.getByName(phrase))
    }

    override fun handleGetById(id: Long): RegionDto {
        val region = regionService.getById(id)
        return RegionMapper.INSTANCE.map(region)
    }

    override fun updateRegion(region: RegionDto): RegionDto {
        val updatedRegion = regionService.updateRegion(RegionMapper.INSTANCE.map(region))
        return RegionMapper.INSTANCE.map(updatedRegion)
    }
}