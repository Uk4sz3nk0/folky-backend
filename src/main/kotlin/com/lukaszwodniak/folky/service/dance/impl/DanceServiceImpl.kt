package com.lukaszwodniak.folky.service.dance.impl

import com.lukaszwodniak.folky.error.NoSuchDanceException
import com.lukaszwodniak.folky.model.Dance
import com.lukaszwodniak.folky.repository.DanceRepository
import com.lukaszwodniak.folky.service.dance.DanceService
import org.springframework.stereotype.Service

/**
 * DanceServiceImpl
 *
 * Created on: 2024-08-28
 * @author Łukasz Wodniak
 */

@Service
class DanceServiceImpl(
    val danceRepository: DanceRepository
) : DanceService {

    override fun addDance(dance: Dance): Dance {
        // TODO: Add additional operations if needed
        return danceRepository.save(dance)
    }

    override fun deleteDance(id: Long) {
        danceRepository.deleteById(id)
    }

    override fun getDanceById(id: Long): Dance {
        return danceRepository.findById(id).orElseThrow { NoSuchDanceException(id) }
    }

    override fun getDances(): List<Dance> {
        return danceRepository.findAll()
    }

    override fun getDancesByLocale(locale: String): List<Dance> {
        return danceRepository.findAllByLocale(locale)
    }

    override fun getDancesByName(phrase: String): List<Dance> {
        return danceRepository.findAllByNameContainingIgnoreCase(phrase)
    }

    override fun updateDance(dance: Dance): Dance {
        val existingDance = danceRepository.findById(dance.id).orElseThrow { NoSuchDanceException(dance.id) }
        updateExistingDance(existingDance, dance)
        return danceRepository.save(existingDance)
    }

    private fun updateExistingDance(existing: Dance, newData: Dance) {
        existing.name = newData.name
        existing.locale = newData.locale
    }
}