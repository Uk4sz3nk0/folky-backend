package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.DanceHandler
import com.lukaszwodniak.folky.mapper.DanceMapper
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.service.dance.DanceService
import org.springframework.stereotype.Service

/**
 * DanceHandlerImpl
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

@Service
class DanceHandlerImpl(
    val danceService: DanceService
) : DanceHandler {

    override fun handleAddDance(dance: DanceDto): DanceDto {
        val addedDance = danceService.addDance(DanceMapper.INSTANCE.map(dance))
        return DanceMapper.INSTANCE.map(addedDance)
    }

    override fun handleDeleteDance(id: Long) {
        danceService.deleteDance(id)
    }

    override fun handleGetDanceById(id: Long): DanceDto {
        return DanceMapper.INSTANCE.map(danceService.getDanceById(id))
    }

    override fun handleGetDances(): MutableList<DanceDto> {
        return DanceMapper.INSTANCE.map(danceService.getDances())
    }

    override fun handleGetDancesByLocale(locale: String): MutableList<DanceDto> {
        return DanceMapper.INSTANCE.map(danceService.getDancesByLocale(locale))
    }

    override fun handleGetDancesByName(phrase: String): MutableList<DanceDto> {
        return DanceMapper.INSTANCE.map(danceService.getDancesByName(phrase))
    }

    override fun handleUpdateDance(dance: DanceDto): DanceDto {
        val updatedDance = DanceMapper.INSTANCE.map(dance)
        return DanceMapper.INSTANCE.map(danceService.updateDance(updatedDance))
    }
}