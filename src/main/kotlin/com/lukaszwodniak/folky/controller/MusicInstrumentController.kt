package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.MusicInstrumentHandler
import com.lukaszwodniak.folky.rest.music_instrument.specification.api.MusicInstrumentsApi
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
) : MusicInstrumentsApi {

    @EndpointLogger
    override fun addInstrumentType(instrumentType: MusicInstrumentTypeDto?): ResponseEntity<MusicInstrumentTypeDto> {
        val addedType = instrumentType?.let { musicInstrumentHandler.handleAddInstrumentType(it) }
        return ResponseEntity.ok(addedType)
    }

    override fun deleteInstrument(id: Long?): ResponseEntity<Void> {
        id?.let {
            musicInstrumentHandler.handleDeleteInstrument(it)
        }
        return ResponseEntity.ok().build()
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
    override fun updateInstrumentType(instrumentType: MusicInstrumentTypeDto?): ResponseEntity<MusicInstrumentTypeDto> {
        val updatedType = instrumentType?.let { musicInstrumentHandler.handleUpdateInstrumentType(it) }
        return ResponseEntity.ok(updatedType)
    }

    override fun addMusicInstrument(musicInstrument: MusicInstrumentDto?): ResponseEntity<MusicInstrumentDto> {
        val addedInstrument = musicInstrument?.let {
            musicInstrumentHandler.handleAddInstrument(it)
        }
        return ResponseEntity.ok(addedInstrument)
    }

    override fun getInstruments(phrase: String?, typeId: Long?): ResponseEntity<MutableList<MusicInstrumentDto>> {
        val instruments = phrase?.let {
            musicInstrumentHandler.handleGetInstrumentByName(it)
        } ?: typeId?.let { musicInstrumentHandler.handleGetInstrumentByType(it) }
        return ResponseEntity.ok(instruments)
    }

    override fun updateInstrument(id: Long?, musicInstrument: MusicInstrumentDto?): ResponseEntity<MusicInstrumentDto> {
        val updatedInstrument = musicInstrument?.let { instrument ->
            id?.let {
                musicInstrumentHandler.handleUpdateInstrument(instrument)
            }
        }
        return ResponseEntity.ok(updatedInstrument)
    }
}