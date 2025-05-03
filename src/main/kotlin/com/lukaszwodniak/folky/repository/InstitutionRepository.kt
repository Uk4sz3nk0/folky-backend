package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Institution
import org.springframework.data.jpa.repository.JpaRepository

/**
 * InstitutionRepository
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

interface InstitutionRepository : JpaRepository<Institution, Long> {
}