package com.lukaszwodniak.folky.service.translation.impl

import com.lukaszwodniak.folky.model.Translation
import com.lukaszwodniak.folky.repository.TranslationsRepository
import com.lukaszwodniak.folky.service.translation.TranslationService
import org.springframework.stereotype.Service

/**
 * TranslationServiceImpl
 *
 * Created on: 2025-01-12
 * @author Łukasz Wodniak
 */

@Service
class TranslationServiceImpl(
    private val translationRepository: TranslationsRepository
) : TranslationService {

    override fun getValue(language: String, specifier: String): String? {
        val optionalValue = translationRepository.findBySpecifierAndLanguage(specifier, language)
        return optionalValue.map { it.value }.orElse(null)
    }

    override fun getTranslationsByLanguageAndPrefix(language: String, prefix: String): Map<String, String> {
        val translations = translationRepository.findAllByLanguageAndSpecifierContaining(language, prefix)
        return translations.associate { it.specifier!! to it.value }
    }

    override fun getTranslationsBySpecifier(specifier: String): MutableList<Translation> {
        val optionalTranslations = translationRepository.findAllBySpecifier(specifier)
        return optionalTranslations.map { it.toMutableList() }.orElse(mutableListOf())
    }

    override fun saveTranslations(translations: MutableList<Translation>) {
        translationRepository.saveAllAndFlush(translations)
    }

    override fun updateTranslations(translations: MutableList<Translation>) {
        translationRepository.saveAllAndFlush(translations)
    }

    override fun deleteTranslationsBySpecifier(specifier: String) {
        val translationsToDeleteOptional = translationRepository.findAllBySpecifier(specifier)
        translationsToDeleteOptional.ifPresent { translationRepository.deleteAll(it) }
    }

    override fun deleteTranslations(translations: MutableList<Translation>) {
        translationRepository.deleteAll(translations)
    }
}