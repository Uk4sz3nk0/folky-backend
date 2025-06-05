package com.lukaszwodniak.folky.service.dance.impl

import com.lukaszwodniak.folky.error.NoSuchDanceException
import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.model.Translation
import com.lukaszwodniak.folky.repository.DanceRepository
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.TranslationsRepository
import com.lukaszwodniak.folky.service.dance.DanceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * DanceServiceImpl
 *
 * Created on: 2024-08-28
 * @author Łukasz Wodniak
 */

@Service
class DanceServiceImpl(
    private val danceRepository: DanceRepository,
    private val translationsRepository: TranslationsRepository,
    private val dancingTeamRepository: DancingTeamRepository
) : DanceService {

    @Transactional
    override fun addDance(dance: Dance, translations: List<Translation>): Dance {
        val specifier = toScreamingSnakeCase(dance.name)
        val mappedDance = dance.copy(name = specifier)
        val mappedTranslations = translations.map { it.copy(specifier = "${DANCE_TRANSLATE_PREFIX}$specifier") }
        translationsRepository.saveAll(mappedTranslations)
        return danceRepository.save(mappedDance)
    }

    override fun deleteDance(id: Long) {
        val dance = danceRepository.findById(id).get()
        val teams = dance.teams
        teams?.forEach { it.dances?.remove(dance) }
        teams?.let { dancingTeamRepository.saveAll(it) }
        danceRepository.deleteById(id)
    }

    override fun getDanceById(id: Long): Dance {
        return danceRepository.findById(id).orElseThrow { NoSuchDanceException(id) }
    }

    override fun getDances(): List<Dance> {
        val dances = danceRepository.findAll()
        return assignTranslatedNames(dances)
    }

    override fun getDancesByLocale(locale: String): List<Dance> {
        val dances = danceRepository.findAllByLocale(locale)
        return assignTranslatedNames(dances)
    }

    override fun getDancesByName(phrase: String): List<Dance> {
        val dances = danceRepository.findAllByNameContainingIgnoreCase(phrase)
        return assignTranslatedNames(dances)
    }

    override fun assignTranslatedNames(dances: List<Dance>): List<Dance> {
        val translations =
            translationsRepository.findAllByLanguageAndSpecifierContaining(PL_LANG, DANCE_TRANSLATE_PREFIX)
        return dances.map {
            val translation =
                translations.find { translation -> translation.specifier == "${DANCE_TRANSLATE_PREFIX}${it.name}" }
            it.copy(name = translation?.value ?: it.name)
        }
    }

    override fun getDanceTranslations(dance: Dance): List<Translation> {
        val key = dance.name
        return translationsRepository.findAllByLanguageAndSpecifierContaining(PL_LANG, "${DANCE_TRANSLATE_PREFIX}$key")
    }

    private fun toScreamingSnakeCase(input: String): String {
        return input
            .replace(Regex("([a-z])([A-Z])"), "$1_$2")
            .uppercase()
    }

    companion object {
        private const val DANCE_TRANSLATE_PREFIX: String = "DANCES_"
        private const val PL_LANG: String = "pl_PL"
    }

}