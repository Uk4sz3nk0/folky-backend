package com.lukaszwodniak.folky.service.translation

import com.lukaszwodniak.folky.model.Translation
import java.util.*

/**
 * TranslationService
 *
 * Created on: 2025-01-12
 * @author Łukasz Wodniak
 */

interface TranslationService {

    fun getValue(language: String, specifier: String): String?
    fun getTranslationsByLanguageAndPrefix(language: String, prefix: String): Map<String, String>
}