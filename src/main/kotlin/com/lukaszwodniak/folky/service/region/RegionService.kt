package com.lukaszwodniak.folky.service.region

import com.lukaszwodniak.folky.model.Region
import com.lukaszwodniak.folky.model.Translation
import org.springframework.stereotype.Service


/**
 * RegionService
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

@Service
interface RegionService {

    fun addRegion(region: Region, translations: MutableList<Translation>): Region
    fun deleteRegion(id: Long)
    fun getByLocale(locale: String): List<Region>
    fun getByName(phrase: String): List<Region>
    fun getById(id: Long, withTranslations: Boolean?): Region
    fun updateRegion(region: Region, translations: MutableList<Translation>): Region
    fun getRegions(): MutableList<Region>
    fun getTranslations(id: Long): MutableList<Translation>
}