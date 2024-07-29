package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.MusicInstrumentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * MusicInstrumentTypeRepository
 * Created on: 2024-07-28
 * @author Łukasz Wodniak
 */

@Repository
interface MusicInstrumentTypeRepository : JpaRepository<MusicInstrumentType, Long> {
}