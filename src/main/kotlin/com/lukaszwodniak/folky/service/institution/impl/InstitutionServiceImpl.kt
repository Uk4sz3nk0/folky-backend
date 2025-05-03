package com.lukaszwodniak.folky.service.institution.impl

import com.lukaszwodniak.folky.error.NoSuchInstitutionException
import com.lukaszwodniak.folky.model.Institution
import com.lukaszwodniak.folky.repository.InstitutionRepository
import com.lukaszwodniak.folky.service.institution.InstitutionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

/**
 * InstitutionServiceImpl
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Service
class InstitutionServiceImpl(
    private val institutionRepository: InstitutionRepository
) : InstitutionService {

    override fun addInstitution(institution: Institution) {
        institutionRepository.saveAndFlush(institution.copy(filesUUID = UUID.randomUUID()))
    }

    override fun deleteInstitution(id: Long) {
        institutionRepository.deleteById(id)
    }

    override fun getInstitution(id: Long): Institution {
        return institutionRepository.findById(id).orElseThrow { NoSuchInstitutionException(id) }
    }

    override fun getInstitutions(page: Int, size: Int): Page<Institution> {
        val pageRequest = PageRequest.of(page, size)
        return institutionRepository.findAll(pageRequest)
    }

    override fun updateInstitution(id: Long, institution: Institution) {
        val existingInstitution = institutionRepository.findById(id).orElseThrow { NoSuchInstitutionException(id) }
        val updatedInstitution = existingInstitution.copy(
            name = institution.name,
            description = institution.description,
            establishedYear = institution.establishedYear,
            website = institution.website,
            logo = institution.logo,
            address = institution.address,
            contact = institution.contact,
            user = institution.user,
        )
        institutionRepository.saveAndFlush(updatedInstitution)
    }
}