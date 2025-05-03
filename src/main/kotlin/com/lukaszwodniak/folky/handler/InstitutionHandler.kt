package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.institution.specification.models.InstitutionDto
import com.lukaszwodniak.folky.rest.institution.specification.models.PagedInstitutionsDto

/**
 * InstitutionHandler
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

interface InstitutionHandler {

    fun handleAddInstitution(institution: InstitutionDto)
    fun handleDeleteInstitution(id: Long)
    fun handleGetInstitution(id: Long): InstitutionDto
    fun handleGetInstitutions(page: Int, size: Int): PagedInstitutionsDto
    fun handleUpdateInstitution(id: Long, institution: InstitutionDto)
}
