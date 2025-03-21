package com.lukaszwodniak.folky.service.dance

import com.lukaszwodniak.folky.model.Dance
import org.springframework.stereotype.Service

/**
 * DanceService
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

@Service
interface DanceService {

    fun addDance(dance: Dance): Dance
    fun deleteDance(id: Long)
    fun getDanceById(id: Long): Dance
    fun getDances(): List<Dance>
    fun getDancesByLocale(locale: String): List<Dance>
    fun getDancesByName(phrase: String): List<Dance>
    fun updateDance(dance: Dance): Dance

}