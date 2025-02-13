package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Translation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * TranslationsRepository
 *
 * Created on: 2025-01-12
 * @author Łukasz Wodniak
 */

@Repository
interface TranslationsRepository : JpaRepository<Translation, Long> {

    fun findBySpecifierAndLanguage(specifier: String, language: String): Optional<Translation>

    fun findAllByLanguageAndSpecifierContaining(language: String, specifierValue: String): MutableList<Translation>

    fun findAllBySpecifier(specifierValue: String): Optional<List<Translation>>
}