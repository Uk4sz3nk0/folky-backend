package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.RegionHandler
import com.lukaszwodniak.folky.rest.region.specification.api.RegionApi
import com.lukaszwodniak.folky.rest.specification.models.RegionDto
import com.lukaszwodniak.folky.rest.specification.models.TranslationDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * RegionController
 *
 * Created on: 2024-08-22
 * @author Łukasz Wodniak
 */

@RestController
class RegionController(
    private val regionHandler: RegionHandler
) : RegionApi {

    @EndpointLogger
    override fun addRegion(region: RegionDto?): ResponseEntity<RegionDto> {
        val addedRegion = region?.let { regionHandler.handleAddRegion(it) }
        return ResponseEntity.ok(addedRegion)
    }

    @EndpointLogger
    override fun deleteRegion(id: Long?): ResponseEntity<Void> {
        id?.let { regionHandler.handleDeleteRegion(it) }
        return ResponseEntity.ok(null)
    }

    @EndpointLogger
    override fun getByLocale(locale: String?): ResponseEntity<MutableList<RegionDto>> {
        val regions = locale?.let { regionHandler.handleGetByLocale(locale) }
        return ResponseEntity.ok(regions)
    }

    @EndpointLogger
    override fun getRegionById(id: Long?, withTranslations: Boolean?): ResponseEntity<RegionDto> {
        val region = id?.let { regionHandler.handleGetById(it, withTranslations ?: false) }
        return ResponseEntity.ok(region)
    }

    @EndpointLogger
    override fun updateRegion(region: RegionDto?): ResponseEntity<RegionDto> {
        val updatedRegion = region?.let { regionHandler.updateRegion(it) }
        return ResponseEntity.ok(updatedRegion)
    }

    @EndpointLogger
    override fun getRegions(phrase: String?): ResponseEntity<MutableList<RegionDto>> {
        val regions = phrase?.let { regionHandler.handleGetByName(it) } ?: regionHandler.handleGetRegions()
        return ResponseEntity.ok(regions)
    }

    @EndpointLogger
    override fun getRegionTranslations(regionId: Long?): ResponseEntity<MutableList<TranslationDto>> {
        val translations = regionId?.let { regionHandler.handleGetTranslations(it) }
        return ResponseEntity.ok(translations)
    }
}