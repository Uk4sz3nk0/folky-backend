package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.MusicInstrument
import com.lukaszwodniak.folky.model.MusicInstrumentType
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentDto
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentTypeDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * MusicInstrumentMapper
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

@Mapper
interface MusicInstrumentMapper {

    fun map(musicInstrument: MusicInstrument): MusicInstrumentDto
    fun map(musicInstrument: MusicInstrumentDto): MusicInstrument
    fun map(musicInstrumentType: MusicInstrumentType): MusicInstrumentTypeDto
    fun map(musicInstrumentType: MusicInstrumentTypeDto): MusicInstrumentType
    fun map(musicInstruments: List<MusicInstrument>): MutableList<MusicInstrumentDto>
    fun mapTypes(musicInstrumentTypes: List<MusicInstrumentType>): MutableList<MusicInstrumentTypeDto>

    companion object {
        val INSTANCE: MusicInstrumentMapper = Mappers.getMapper(MusicInstrumentMapper::class.java)
    }
}