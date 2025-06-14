package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.InstitutionHandler
import com.lukaszwodniak.folky.rest.institution.specification.api.InstitutionsApi
import com.lukaszwodniak.folky.rest.institution.specification.models.InstitutionDto
import com.lukaszwodniak.folky.rest.institution.specification.models.PagedEventsDto
import com.lukaszwodniak.folky.rest.institution.specification.models.PagedInstitutionsDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * InstitutionController
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@RestController
class InstitutionController(
    private val institutionHandler: InstitutionHandler
) : InstitutionsApi {

    @EndpointLogger
    override fun addInstitution(institution: InstitutionDto?): ResponseEntity<Void> {
        institution?.let { institutionHandler.handleAddInstitution(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deleteInstitution(id: Long?): ResponseEntity<Void> {
        id?.let { institutionHandler.handleDeleteInstitution(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getInstitution(id: Long?): ResponseEntity<InstitutionDto> {
        val institution = id?.let { institutionHandler.handleGetInstitution(it) }
        return ResponseEntity.ok(institution)
    }

    @EndpointLogger
    override fun getInstitutions(page: Int?, size: Int?): ResponseEntity<PagedInstitutionsDto> {
        val institutions =
            institutionHandler.handleGetInstitutions(
                page ?: ControllerCommons.DEFAULT_PAGE,
                size ?: ControllerCommons.DEFAULT_PAGE_SIZE
            )
        return ResponseEntity.ok(institutions)
    }

    @EndpointLogger
    override fun updateInstitution(id: Long?, institution: InstitutionDto?): ResponseEntity<Void> {
        id?.let { institutionId ->
            institution?.let { institutionHandler.handleUpdateInstitution(institutionId, it) }
        }
        return ResponseEntity.ok().build()
    }

    override fun getEvents(id: Long?, page: Long?, size: Long?): ResponseEntity<PagedEventsDto> {
        TODO("Not yet implemented")
    }

}