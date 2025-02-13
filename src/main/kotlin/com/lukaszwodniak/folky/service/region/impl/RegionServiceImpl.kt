package com.lukaszwodniak.folky.service.region.impl

import com.lukaszwodniak.folky.error.NoSuchRegionException
import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.model.Translation
import com.lukaszwodniak.folky.repository.RegionRepository
import com.lukaszwodniak.folky.service.region.RegionService
import com.lukaszwodniak.folky.service.translation.TranslationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
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

    @Transactional
    override fun addRegion(region: Region, translations: MutableList<Translation>): Region {
        // TODO: Add required logic
        // TODO: Add exists by name exception
        val mappedName = mapRegionName(region.name)
        val translationsToSave = translations
            .filter { it.value.isNotBlank() && it.language.isNotBlank() }
            .onEach { it.specifier = "${TRANSLATION_PREFIX}_$mappedName" }
            .toMutableList()
        translationService.saveTranslations(translationsToSave)
        return regionRepository.saveAndFlush(region.copy(name = mappedName))
    }

    override fun deleteRegion(id: Long) {
        val region = regionRepository.findById(id).orElseThrow { NoSuchRegionException(id) }
        translationService.deleteTranslationsBySpecifier(region.name)
        regionRepository.deleteById(id)
    }

    override fun getByLocale(locale: String): List<Region> {
        return regionRepository.findAllByLocale(locale)
    }

    override fun getByName(phrase: String): List<Region> {
        return regionRepository.findAllByNameContainingIgnoreCase(phrase)
    }

    override fun getById(id: Long, withTranslations: Boolean?): Region {
        val region = regionRepository.findById(id).orElseThrow { NoSuchRegionException(id) }
        val language = httpRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE) ?: DEFAULT_TRANSLATION_LANGUAGE
        val translations = translationService.getTranslationsByLanguageAndPrefix(language, TRANSLATION_PREFIX)
        val translation = translations["${TRANSLATION_PREFIX}_${region.name}"]
        return if (!withTranslations!!) {
            translation?.let {
                region.copy(name = it)
            } ?: region
        } else {
            region
        }
    }

    override fun updateRegion(region: Region, translations: MutableList<Translation>): Region {
        val existingRegion = regionRepository.findById(region.id).orElseThrow { NoSuchRegionException(region.id) }
        updateRegion(existingRegion, region)
        updateRegionTranslations(region, translations)
        return regionRepository.saveAndFlush(region)
    }

    override fun getRegions(): MutableList<Region> {
        val regions = regionRepository.findAll().toMutableList()
        val language = httpRequest.getHeader(HttpHeaders.ACCEPT_LANGUAGE) ?: DEFAULT_TRANSLATION_LANGUAGE
        val translations = translationService.getTranslationsByLanguageAndPrefix(language, TRANSLATION_PREFIX)
        return regions.map { it.copy(name = translations.get("${TRANSLATION_PREFIX}_${it.name}")!!) }.toMutableList()
    }

    override fun getTranslations(id: Long): MutableList<Translation> {
        val region = regionRepository.findById(id).orElseThrow { NoSuchRegionException(id) }
        return translationService.getTranslationsBySpecifier("${TRANSLATION_PREFIX}_${region.name}")
    }

    private fun updateRegion(existing: Region, newData: Region) {
        existing.name = newData.name
        existing.locale = newData.locale
    }

    private fun updateRegionTranslations(region: Region, translations: MutableList<Translation>) {
        val mappedName = "${TRANSLATION_PREFIX}_" + mapRegionName(region.name)
        val existingTranslations = translationService.getTranslationsBySpecifier(mappedName)

        val existingTranslationsMap = existingTranslations.associateBy { it.language }
        val newTranslationsMap = translations.associateBy { it.language }

        val translationsToUpdate = existingTranslations.filter { it.language in newTranslationsMap }
            .map { existing -> existing.apply { value = newTranslationsMap[existing.language]!!.value } }
            .toMutableList()

        val translationsToAdd = translations.filter { it.language !in existingTranslationsMap }.toMutableList()
        val translationsToDelete = existingTranslations.filter { it.language !in newTranslationsMap }.toMutableList()

        translationService.updateTranslations(translationsToUpdate)
        translationService.saveTranslations(translationsToAdd)
        translationService.deleteTranslations(translationsToDelete)

    }

    private fun mapRegionName(name: String): String {
        return name.trim().uppercase()
            .replace(MULTIPLE_SPACE_REGEX, " ")
            .replace(" ", "_")
    }

    companion object {
        private const val TRANSLATION_PREFIX = "REGIONS"
        private const val DEFAULT_TRANSLATION_LANGUAGE = "pl_PL"
        private val MULTIPLE_SPACE_REGEX = Regex("\\s+")
    }
}