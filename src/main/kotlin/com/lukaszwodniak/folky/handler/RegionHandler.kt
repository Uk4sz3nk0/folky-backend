package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.RegionDto

/**
 * RegionHandler
 * <br><br>
 * Created on: 2024-09-05
 * @author Łukasz Wodniak
 */

interface RegionHandler {

    fun handleAddRegion(region: RegionDto): RegionDto
    fun handleDeleteRegion(id: Long)
    fun handleGetByLocale(locale: String): MutableList<RegionDto>
    fun handleGetByName(phrase: String): MutableList<RegionDto>
    fun handleGetById(id: Long): RegionDto
    fun updateRegion(region: RegionDto): RegionDto
}