package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Dance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * DanceRepository
 * Created on: 2024-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@studnet.krakow.pl)
 */

@Repository
interface DanceRepository : JpaRepository<Dance, Long> {

    fun findAllByLocale(locale: String): List<Dance>

    fun findAllByNameContainingIgnoreCase(phrase: String): List<Dance>
}