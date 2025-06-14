package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.DancingTeam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/**
 * DancingTeamRepository
 *
 * Created on: 2024-08-09
 * @author Łukasz Wodniak
 */

interface DancingTeamRepository : JpaRepository<DancingTeam, Long>, JpaSpecificationExecutor<DancingTeam> {

    fun findAllByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(
        phrase: String,
        phrase1: String,
        pageable: Pageable
    ): Page<DancingTeam>

    fun existsByNameIgnoreCase(name: String): Boolean
}