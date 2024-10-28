package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.MusicInstrumentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * MusicInstrumentTypeRepository
 * Created on: 2024-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@studnet.krakow.pl)
 */

@Repository
interface MusicInstrumentTypeRepository : JpaRepository<MusicInstrumentType, Long> {

    fun findAllByNameContainingIgnoreCase(phrase: String): Optional<List<MusicInstrumentType>>
}