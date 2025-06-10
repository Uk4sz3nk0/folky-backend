package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.model.Translation
import com.lukaszwodniak.folky.rest.specification.models.DanceDto
import com.lukaszwodniak.folky.rest.specification.models.DanceTranslationDto
import com.lukaszwodniak.folky.rest.specification.models.PagedDancesDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * DanceMapper
 *
 * Created on: 2024-08-12
 * @author Łukasz Wodniak
 */

@Mapper
interface DanceMapper {

    fun map(dance: Dance): DanceDto
    fun map(dance: DanceDto): Dance

    fun map(dances: List<Dance>): MutableList<DanceDto>
    fun mapPagedToDto(dances: Page<Dance>): PagedDancesDto
    fun mapDancesFromDto(dances: List<DanceDto>): List<Dance>

    @Mapping(source = "locale", target = "language")
    fun mapDanceTranslationFromDto(translation: DanceTranslationDto): Translation
    fun mapDanceTranslationsFromDto(dances: List<DanceTranslationDto>): List<Translation>

    fun mapDanceTranslationToDto(translation: Translation): DanceTranslationDto
    fun mapDanceTranslationsToDto(translations: List<Translation>): List<DanceTranslationDto>

    companion object {
        val INSTANCE: DanceMapper = Mappers.getMapper(DanceMapper::class.java)
    }
}