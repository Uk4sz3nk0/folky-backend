package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DanceRequestDto

/**
 * DanceHandler
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

interface DanceHandler {

    fun handleAddDance(danceRequest: DanceRequestDto): DanceDto
    fun handleDeleteDance(id: Long)
    fun handleGetDanceById(id: Long): DanceRequestDto
    fun handleGetDances(): MutableList<DanceDto>
    fun handleGetDancesByLocale(locale: String): MutableList<DanceDto>
    fun handleGetDancesByName(phrase: String): MutableList<DanceDto>
}