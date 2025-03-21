package com.lukaszwodniak.folky.service.musicInstrument

import com.lukaszwodniak.folky.model.MusicInstrument
import com.lukaszwodniak.folky.model.MusicInstrumentType
import org.springframework.stereotype.Service

/**
 * MusicInstrument
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

@Service
interface MusicInstrumentService {

    fun addInstrumentType(instrumentType: MusicInstrumentType): MusicInstrumentType
    fun addInstrument(instrument: MusicInstrument): MusicInstrument
    fun deleteInstrumentType(id: Long)
    fun deleteInstrument(id: Long)
    fun getInstrumentById(id: Long): MusicInstrument
    fun getInstrumentTypeById(id: Long): MusicInstrumentType
    fun getInstrumentTypeByName(phrase: String): List<MusicInstrumentType>
    fun getInstrumentByName(phrase: String): List<MusicInstrument>
    fun getInstrumentByType(type: MusicInstrumentType): List<MusicInstrument>
    fun updateInstrument(instrument: MusicInstrument): MusicInstrument
    fun updateInstrumentType(type: MusicInstrumentType): MusicInstrumentType

}