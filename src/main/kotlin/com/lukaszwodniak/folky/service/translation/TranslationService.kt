package com.lukaszwodniak.folky.service.translation

import com.lukaszwodniak.folky.model.Translation
import org.springframework.stereotype.Service
import java.util.*

/**
 * TranslationService
 *
 * Created on: 2025-01-12
 * @author Łukasz Wodniak
 */

@Service
interface TranslationService {

    fun getValue(language: String, specifier: String): String?
    fun getTranslationsByLanguageAndPrefix(language: String, prefix: String): Map<String, String>
    fun getTranslationsBySpecifier(specifier: String): MutableList<Translation>
    fun saveTranslations(translations: MutableList<Translation>)
    fun updateTranslations(translations: MutableList<Translation>)
    fun deleteTranslationsBySpecifier(specifier: String)
    fun deleteTranslations(translations: MutableList<Translation>)
}