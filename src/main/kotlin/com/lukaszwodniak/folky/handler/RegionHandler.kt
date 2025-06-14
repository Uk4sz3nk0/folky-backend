package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.RegionDto
import com.lukaszwodniak.folky.rest.specification.models.TranslationDto

/**
 * RegionHandler
 *
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

interface RegionHandler {

    fun handleAddRegion(region: RegionDto): RegionDto
    fun handleDeleteRegion(id: Long)
    fun handleGetByLocale(locale: String): MutableList<RegionDto>
    fun handleGetByName(phrase: String): MutableList<RegionDto>
    fun handleGetById(id: Long, withTranslations: Boolean?): RegionDto
    fun updateRegion(region: RegionDto): RegionDto
    fun handleGetRegions(): MutableList<RegionDto>
    fun handleGetTranslations(id: Long): MutableList<TranslationDto>
}