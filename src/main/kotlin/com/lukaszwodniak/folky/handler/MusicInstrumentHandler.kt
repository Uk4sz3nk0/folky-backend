package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentDto
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentTypeDto

/**
 * MusicInstrumentHandler
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

interface MusicInstrumentHandler {

    fun handleAddInstrumentType(instrumentType: MusicInstrumentTypeDto): MusicInstrumentTypeDto
    fun handleAddInstrument(instrument: MusicInstrumentDto): MusicInstrumentDto
    fun handleDeleteInstrumentType(id: Long)
    fun handleDeleteInstrument(id: Long)
    fun handleGetInstrumentById(id: Long): MusicInstrumentDto
    fun handleGetInstrumentTypeById(id: Long): MusicInstrumentTypeDto
    fun handleGetInstrumentTypeByName(phrase: String): MutableList<MusicInstrumentTypeDto>
    fun handleGetInstrumentByName(phrase: String): MutableList<MusicInstrumentDto>
    fun handleGetInstrumentByType(typeId: Long): MutableList<MusicInstrumentDto>
    fun handleUpdateInstrument(instrument: MusicInstrumentDto): MusicInstrumentDto
    fun handleUpdateInstrumentType(type: MusicInstrumentTypeDto): MusicInstrumentTypeDto
}