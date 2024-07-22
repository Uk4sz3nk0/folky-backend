package com.lukaszwodniak.folky.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * MusicInstrumentRepository
 * Created on: 2024-07-29
 * @author Łukasz Wodniak
 */

@Repository
interface MusicInstrumentRepository : JpaRepository<MusicInstrumentRepository, Long> {
}