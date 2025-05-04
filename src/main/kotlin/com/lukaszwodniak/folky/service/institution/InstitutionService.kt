package com.lukaszwodniak.folky.service.institution

import com.lukaszwodniak.folky.model.Institution
import org.springframework.data.domain.Page

/**
 * InstitutionService
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

interface InstitutionService {

    fun addInstitution(institution: Institution)
    fun deleteInstitution(id: Long)
    fun getInstitution(id: Long): Institution
    fun getInstitutions(page: Int, size: Int): Page<Institution>
    fun updateInstitution(id: Long, institution: Institution)
}