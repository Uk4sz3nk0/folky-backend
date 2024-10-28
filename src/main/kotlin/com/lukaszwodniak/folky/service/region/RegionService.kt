package com.lukaszwodniak.folky.service.region

import com.lukaszwodniak.folky.model.Region


/**
 * RegionService
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

interface RegionService {

    fun addRegion(region: Region): Region
    fun deleteRegion(id: Long)
    fun getByLocale(locale: String): List<Region>
    fun getByName(phrase: String): List<Region>
    fun getById(id: Long): Region
    fun updateRegion(region: Region): Region
}