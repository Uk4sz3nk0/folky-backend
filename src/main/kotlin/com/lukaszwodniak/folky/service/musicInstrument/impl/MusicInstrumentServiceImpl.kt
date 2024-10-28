package com.lukaszwodniak.folky.service.musicInstrument.impl

import com.lukaszwodniak.folky.error.NoSuchMusicInstrumentException
import com.lukaszwodniak.folky.error.NoSuchMusicInstrumentTypeException
import com.lukaszwodniak.folky.model.MusicInstrument
import com.lukaszwodniak.folky.model.MusicInstrumentType
import com.lukaszwodniak.folky.repository.MusicInstrumentRepository
import com.lukaszwodniak.folky.repository.MusicInstrumentTypeRepository
import com.lukaszwodniak.folky.service.musicInstrument.MusicInstrumentService
import org.springframework.stereotype.Service

/**
 * MusicInstrumentServiceImpl
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

@Service
class MusicInstrumentServiceImpl(
    val musicInstrumentRepository: MusicInstrumentRepository,
    val musicInstrumentTypeRepository: MusicInstrumentTypeRepository
) : MusicInstrumentService {

    override fun addInstrumentType(instrumentType: MusicInstrumentType): MusicInstrumentType {
        return musicInstrumentTypeRepository.save(instrumentType)
    }

    override fun addInstrument(instrument: MusicInstrument): MusicInstrument {
        return musicInstrumentRepository.save(instrument)
    }

    override fun deleteInstrumentType(id: Long) {
        musicInstrumentTypeRepository.deleteById(id)
    }

    override fun deleteInstrument(id: Long) {
        musicInstrumentRepository.deleteById(id)
    }

    override fun getInstrumentById(id: Long): MusicInstrument {
        return musicInstrumentRepository.findById(id).orElseThrow { NoSuchMusicInstrumentException(id) }
    }

    override fun getInstrumentTypeById(id: Long): MusicInstrumentType {
        return musicInstrumentTypeRepository.findById(id).orElseThrow { NoSuchMusicInstrumentTypeException(id) }
    }

    override fun getInstrumentTypeByName(phrase: String): List<MusicInstrumentType> {
        return musicInstrumentTypeRepository.findAllByNameContainingIgnoreCase(phrase).orElse(emptyList())
    }

    override fun getInstrumentByName(phrase: String): List<MusicInstrument> {
        return musicInstrumentRepository.findAllByNameContainingIgnoreCase(phrase).orElse(emptyList())
    }

    override fun getInstrumentByType(type: MusicInstrumentType): List<MusicInstrument> {
        return musicInstrumentRepository.findAllByType(type).orElse(emptyList())
    }

    override fun updateInstrument(instrument: MusicInstrument): MusicInstrument {
        val existingInstrument = musicInstrumentRepository.findById(instrument.id)
            .orElseThrow { NoSuchMusicInstrumentException(instrument.id) }
        updateExistingInstrument(existingInstrument, instrument)
        return musicInstrumentRepository.save(existingInstrument)
    }

    override fun updateInstrumentType(type: MusicInstrumentType): MusicInstrumentType {
        val existingType =
            musicInstrumentTypeRepository.findById(type.id).orElseThrow { NoSuchMusicInstrumentTypeException(type.id) }
        updateExistingType(existingType, type)
        return musicInstrumentTypeRepository.save(existingType)
    }

    private fun updateExistingType(existingType: MusicInstrumentType, typeNewData: MusicInstrumentType) {
        existingType.name = typeNewData.name
    }

    private fun updateExistingInstrument(existingInstrument: MusicInstrument, newInstrumentData: MusicInstrument) {
        existingInstrument.name = newInstrumentData.name
        existingInstrument.type = newInstrumentData.type
    }
}