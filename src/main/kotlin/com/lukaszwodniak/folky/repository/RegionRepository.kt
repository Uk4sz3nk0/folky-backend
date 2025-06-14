package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * RegionRepository
 *
 * Created on: 2024-07-28
 * @author Łukasz Wodniak
 */

@Repository
interface RegionRepository : JpaRepository<Region, Long> {

    fun findAllByLocale(locale: String): List<Region>
    fun findAllByNameContainingIgnoreCase(name: String): List<Region>
}