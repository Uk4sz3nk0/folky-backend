package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.MusicInstrumentHandler
import com.lukaszwodniak.folky.rest.music_instrument.specification.api.MusicInstrumentApi
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentDto
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentTypeDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * MusicController
 *
 * Created on: 2024-08-22
 * @author Łukasz Wodniak
 */

@RestController
class MusicInstrumentController(
    val musicInstrumentHandler: MusicInstrumentHandler
) : MusicInstrumentApi {

    @EndpointLogger
    override fun addInstrumentType(instrumentType: MusicInstrumentTypeDto?): ResponseEntity<MusicInstrumentTypeDto> {
        val addedType = instrumentType?.let { musicInstrumentHandler.handleAddInstrumentType(it) }
        return ResponseEntity.ok(addedType)
    }

    @EndpointLogger
    override fun addInstument(instrument: MusicInstrumentDto?): ResponseEntity<MusicInstrumentDto> {
        val addedInstrument = instrument?.let { musicInstrumentHandler.handleAddInstrument(it) }
        return ResponseEntity.ok(addedInstrument)
    }

    @EndpointLogger
    override fun deleteInstrument(id: Long?): ResponseEntity<Void> {
        id?.let { musicInstrumentHandler.handleDeleteInstrument(it) }
        return ResponseEntity.ok(null)
    }

    @EndpointLogger
    override fun deleteInstrumentType(id: Long?): ResponseEntity<Void> {
        id?.let { musicInstrumentHandler.handleDeleteInstrumentType(it) }
        return ResponseEntity.ok(null)
    }

    @EndpointLogger
    override fun getInstrumentById(id: Long?): ResponseEntity<MusicInstrumentDto> {
        val instrument = id?.let { musicInstrumentHandler.handleGetInstrumentById(it) }
        return ResponseEntity.ok(instrument)
    }

    @EndpointLogger
    override fun getInstrumentTypeById(id: Long?): ResponseEntity<MusicInstrumentTypeDto> {
        val type = id?.let { musicInstrumentHandler.handleGetInstrumentTypeById(it) }
        return ResponseEntity.ok(type)
    }

    @EndpointLogger
    override fun getInstrumentTypesByName(phrase: String?): ResponseEntity<MutableList<MusicInstrumentTypeDto>> {
        val instruments = phrase?.let { musicInstrumentHandler.handleGetInstrumentTypeByName(it) }
        return ResponseEntity.ok(instruments)
    }

    @EndpointLogger
    override fun getInstrumentsByName(phrase: String?): ResponseEntity<MutableList<MusicInstrumentDto>> {
        val types = phrase?.let { musicInstrumentHandler.handleGetInstrumentByName(it) }
        return ResponseEntity.ok(types)
    }

    @EndpointLogger
    override fun getInstrumentsByType(typeId: Long?): ResponseEntity<MutableList<MusicInstrumentDto>> {
        val instruments = typeId?.let { musicInstrumentHandler.handleGetInstrumentByType(it) }
        return ResponseEntity.ok(instruments)
    }

    @EndpointLogger
    override fun updateInstrument(instrument: MusicInstrumentDto?): ResponseEntity<MusicInstrumentDto> {
        val updatedInstrument = instrument?.let { musicInstrumentHandler.handleUpdateInstrument(it) }
        return ResponseEntity.ok(updatedInstrument)
    }

    @EndpointLogger
    override fun updateInstrumentType(instrumentType: MusicInstrumentTypeDto?): ResponseEntity<MusicInstrumentTypeDto> {
        val updatedType = instrumentType?.let { musicInstrumentHandler.handleUpdateInstrumentType(it) }
        return ResponseEntity.ok(updatedType)
    }
}