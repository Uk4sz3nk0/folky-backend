package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.DanceHandler
import com.lukaszwodniak.folky.mapper.DanceMapper
import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DanceRequestDto
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

    override fun handleAddDance(danceRequest: DanceRequestDto): DanceDto {
        val mappedDance = Dance(id = danceRequest.id, locale = danceRequest.locale, name = danceRequest.name)
        val translations = DanceMapper.INSTANCE.mapDanceTranslationsFromDto(danceRequest.translations)

        return DanceMapper.INSTANCE.map(danceService.addDance(mappedDance, translations))
    }

    override fun handleDeleteDance(id: Long) {
        danceService.deleteDance(id)
    }

    override fun handleGetDanceById(id: Long): DanceRequestDto {
        val dance = danceService.getDanceById(id)
        val danceTranslations = danceService.getDanceTranslations(dance)

        return DanceRequestDto().apply {
            name = dance.name
            locale = dance.locale
            translations = DanceMapper.INSTANCE.mapDanceTranslationsToDto(danceTranslations)
        }.id(dance.id)
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
}