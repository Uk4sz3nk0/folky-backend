package com.lukaszwodniak.folky.service.region.impl

import com.lukaszwodniak.folky.error.NoSuchRegionException
import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.repository.RegionRepository
import com.lukaszwodniak.folky.service.region.RegionService
import com.lukaszwodniak.folky.service.translation.TranslationService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

/**
 * RegionServiceImpl
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

@Service
class RegionServiceImpl(
    val regionRepository: RegionRepository,
    val httpRequest: HttpServletRequest,
    val translationService: TranslationService
) : RegionService {

    override fun addRegion(region: Region): Region {
        // TODO: Add required logic
        // TODO: Add exists by name exception
        return regionRepository.saveAndFlush(region)
    }

    override fun deleteRegion(id: Long) {
        regionRepository.deleteById(id)
    }

    override fun getByLocale(locale: String): List<Region> {
        return regionRepository.findAllByLocale(locale)
    }

    override fun getByName(phrase: String): List<Region> {
        return regionRepository.findAllByNameContainingIgnoreCase(phrase)
    }

    override fun getById(id: Long): Region {
        return regionRepository.findById(id).orElseThrow { NoSuchRegionException(id) }
    }

    override fun updateRegion(region: Region): Region {
        val existingRegion = regionRepository.findById(region.id).orElseThrow { NoSuchRegionException(region.id) }
        updateRegion(existingRegion, region)
        return regionRepository.saveAndFlush(region)
    }

    override fun getRegions(): MutableList<Region> {
        val regions = regionRepository.findAll().toMutableList()
        val language = httpRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE) ?: DEFAULT_TRANSLATION_LANGUAGE
        val translations = translationService.getTranslationsByLanguageAndPrefix(language, TRANSLATION_PREFIX)
        return regions.map { it.copy(name = translations.get("${TRANSLATION_PREFIX}_${it.name}")!!) }.toMutableList()
    }

    private fun updateRegion(existing: Region, newData: Region) {
        existing.name = newData.name
        existing.locale = newData.locale
    }

    companion object {
        private const val TRANSLATION_PREFIX = "REGIONS"
        private const val DEFAULT_TRANSLATION_LANGUAGE = "pl_PL"
    }
}