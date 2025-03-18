package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Region
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

/**
 * DancingTeamRepository
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

interface DancingTeamRepository : JpaRepository<DancingTeam, Long>, JpaSpecificationExecutor<DancingTeam> {

    fun findAllByRegion(region: Region): Optional<List<DancingTeam>>

    fun findAllByNameContainsIgnoreCase(phrase: String): Optional<List<DancingTeam>>
    fun findAllByNameContainsIgnoreCase(phrase: String, pageable: Pageable): Page<DancingTeam>

    fun existsByNameIgnoreCase(name: String): Boolean
}