package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.rest.specification.models.DanceDto

/**
 * DanceHandler
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

interface DanceHandler {

    fun handleAddDance(dance: DanceDto): DanceDto
    fun handleDeleteDance(id: Long)
    fun handleGetDanceById(id: Long): DanceDto
    fun handleGetDances(): MutableList<DanceDto>
    fun handleGetDancesByLocale(locale: String): MutableList<DanceDto>
    fun handleGetDancesByName(phrase: String): MutableList<DanceDto>
    fun handleUpdateDance(dance: DanceDto): DanceDto
}