package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Translation
import com.lukaszwodniak.folky.rest.specification.models.TranslationDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * TranslationMapper
 *
 * Created on: 2025-02-16
 * @author Łukasz Wodniak
 */

@Mapper
interface TranslationMapper {

    @Mapping(source = "language", target = "locale")
    fun mapToDto(translation: Translation): TranslationDto
    fun mapToDto(translations: MutableList<Translation>): MutableList<TranslationDto>

    @Mapping(source = "locale", target = "language")
    fun mapFromDto(translation: TranslationDto): Translation
    fun mapFromDto(translations: MutableList<TranslationDto>): MutableList<Translation>

    companion object {
        val INSTANCE = Mappers.getMapper(TranslationMapper::class.java)
    }
}