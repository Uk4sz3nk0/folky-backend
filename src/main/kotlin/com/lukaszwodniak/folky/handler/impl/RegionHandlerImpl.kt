package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.RegionHandler
import com.lukaszwodniak.folky.mapper.RegionMapper
import com.lukaszwodniak.folky.mapper.TranslationMapper
import com.lukaszwodniak.folky.rest.specification.models.RegionDto
import com.lukaszwodniak.folky.rest.specification.models.TranslationDto
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
        val mappedTranslations = TranslationMapper.INSTANCE.mapFromDto(region.translations)
        val addedRegion = regionService.addRegion(RegionMapper.INSTANCE.map(region), mappedTranslations)
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

    override fun handleGetById(id: Long, withTranslations: Boolean?): RegionDto {
        val region = regionService.getById(id, withTranslations ?: false)
        val mappedRegion = RegionMapper.INSTANCE.map(region)
        if (withTranslations!!) {
            val translations = regionService.getTranslations(id)
            val mapped = TranslationMapper.INSTANCE.mapToDto(translations)
            mappedRegion.translations = mapped
        }
        return mappedRegion
    }

    override fun updateRegion(region: RegionDto): RegionDto {
        val translations = TranslationMapper.INSTANCE.mapFromDto(region.translations)
        val updatedRegion = regionService.updateRegion(RegionMapper.INSTANCE.map(region), translations)
        return RegionMapper.INSTANCE.map(updatedRegion)
    }

    override fun handleGetRegions(): MutableList<RegionDto> {
        val regions = regionService.getRegions()
        return RegionMapper.INSTANCE.map(regions)
    }

    override fun handleGetTranslations(id: Long): MutableList<TranslationDto> {
        val translations = regionService.getTranslations(id)
        return TranslationMapper.INSTANCE.mapToDto(translations)
    }
}