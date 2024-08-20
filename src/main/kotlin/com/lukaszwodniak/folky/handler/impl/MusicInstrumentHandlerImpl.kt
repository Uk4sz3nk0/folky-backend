package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.error.NoSuchDanceTypeException
import com.lukaszwodniak.folky.handler.MusicInstrumentHandler
import com.lukaszwodniak.folky.mapper.MusicInstrumentMapper
import com.lukaszwodniak.folky.repository.MusicInstrumentTypeRepository
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentDto
import com.lukaszwodniak.folky.rest.specification.models.MusicInstrumentTypeDto
import com.lukaszwodniak.folky.service.musicInstrument.MusicInstrumentService
import org.springframework.stereotype.Service

/**
 * MusicInstrumentHandlerImpl
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

@Service
class MusicInstrumentHandlerImpl(
    val musicInstrumentService: MusicInstrumentService,
    val musicInstrumentTypeRepository: MusicInstrumentTypeRepository
) : MusicInstrumentHandler {

    override fun handleAddInstrumentType(instrumentType: MusicInstrumentTypeDto): MusicInstrumentTypeDto {
        val addedType = musicInstrumentService.addInstrumentType(MusicInstrumentMapper.INSTANCE.map(instrumentType))
        return MusicInstrumentMapper.INSTANCE.map(addedType)
    }

    override fun handleAddInstrument(instrument: MusicInstrumentDto): MusicInstrumentDto {
        val addedInstrument = musicInstrumentService.addInstrument(MusicInstrumentMapper.INSTANCE.map(instrument))
        return MusicInstrumentMapper.INSTANCE.map(addedInstrument)
    }

    override fun handleDeleteInstrumentType(id: Long) {
        musicInstrumentService.deleteInstrumentType(id)
    }

    override fun handleDeleteInstrument(id: Long) {
        musicInstrumentService.deleteInstrument(id)
    }

    override fun handleGetInstrumentById(id: Long): MusicInstrumentDto {
        return MusicInstrumentMapper.INSTANCE.map(musicInstrumentService.getInstrumentById(id))
    }

    override fun handleGetInstrumentTypeById(id: Long): MusicInstrumentTypeDto {
        return MusicInstrumentMapper.INSTANCE.map(musicInstrumentService.getInstrumentTypeById(id))
    }

    override fun handleGetInstrumentTypeByName(phrase: String): MutableList<MusicInstrumentTypeDto> {
        return MusicInstrumentMapper.INSTANCE.mapTypes(musicInstrumentService.getInstrumentTypeByName(phrase))
    }

    override fun handleGetInstrumentByName(phrase: String): MutableList<MusicInstrumentDto> {
        return MusicInstrumentMapper.INSTANCE.map(musicInstrumentService.getInstrumentByName(phrase))
    }

    override fun handleGetInstrumentByType(typeId: Long): MutableList<MusicInstrumentDto> {
        val type = musicInstrumentTypeRepository.findById(typeId).orElseThrow { NoSuchDanceTypeException(typeId) }
        return MusicInstrumentMapper.INSTANCE.map(musicInstrumentService.getInstrumentByType(type))
    }

    override fun handleUpdateInstrument(instrument: MusicInstrumentDto): MusicInstrumentDto {
        val updatedInstrument = musicInstrumentService.updateInstrument(MusicInstrumentMapper.INSTANCE.map(instrument))
        return MusicInstrumentMapper.INSTANCE.map(updatedInstrument)
    }

    override fun handleUpdateInstrumentType(type: MusicInstrumentTypeDto): MusicInstrumentTypeDto {
        val updatedType = musicInstrumentService.updateInstrumentType(MusicInstrumentMapper.INSTANCE.map(type))
        return MusicInstrumentMapper.INSTANCE.map(updatedType)
    }
}