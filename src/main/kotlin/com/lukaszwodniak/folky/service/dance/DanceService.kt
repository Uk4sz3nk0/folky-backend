package com.lukaszwodniak.folky.service.dance

import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.model.Translation

/**
 * DanceService
 *
 * Created on: 2024-08-27
 * @author Łukasz Wodniak
 */

interface DanceService {

    fun addDance(dance: Dance, translations: List<Translation>): Dance
    fun deleteDance(id: Long)
    fun getDanceById(id: Long): Dance
    fun getDances(): List<Dance>
    fun getDancesByLocale(locale: String): List<Dance>
    fun getDancesByName(phrase: String): List<Dance>
    fun assignTranslatedNames(dances: List<Dance>): List<Dance>
    fun getDanceTranslations(dance: Dance): List<Translation>

}