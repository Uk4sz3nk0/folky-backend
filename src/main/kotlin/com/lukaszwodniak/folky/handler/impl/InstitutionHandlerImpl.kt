package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.InstitutionHandler
import com.lukaszwodniak.folky.mapper.InstitutionMapper
import com.lukaszwodniak.folky.repository.ContactRepository
import com.lukaszwodniak.folky.rest.institution.specification.models.InstitutionDto
import com.lukaszwodniak.folky.rest.institution.specification.models.PagedInstitutionsDto
import com.lukaszwodniak.folky.service.address.AddressService
import com.lukaszwodniak.folky.service.institution.InstitutionService
import com.lukaszwodniak.folky.service.users.UserService
import org.springframework.stereotype.Service

/**
 * InstitutionHandler
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Service
class InstitutionHandlerImpl(
    private val institutionService: InstitutionService,
    private val addressService: AddressService,
    private val userService: UserService,
    private val contactRepository: ContactRepository,
) : InstitutionHandler {

    override fun handleAddInstitution(institution: InstitutionDto) {
        val mappedInstitution = InstitutionMapper.INSTANCE.mapFromDto(institution)
        institutionService.addInstitution(mappedInstitution)
    }

    override fun handleDeleteInstitution(id: Long) {
        institutionService.deleteInstitution(id)
    }

    override fun handleGetInstitution(id: Long): InstitutionDto {
        val institution = institutionService.getInstitution(id)
        return InstitutionMapper.INSTANCE.mapToDto(institution)
    }

    override fun handleGetInstitutions(page: Int, size: Int): PagedInstitutionsDto {
        val institutions = institutionService.getInstitutions(page, size)
        return InstitutionMapper.INSTANCE.mapToPageable(institutions)
    }

    override fun handleUpdateInstitution(id: Long, institution: InstitutionDto) {
        val mappedInstitution = InstitutionMapper.INSTANCE.mapFromDto(institution)
        val address = addressService.getAddress(institution.addressId)
        val contact = contactRepository.findById(institution.contactId)
            .orElseThrow { NoSuchElementException("No such contact with id ${institution.contactId}") }
        val user = userService.getUserById(institution.userId)

        user?.let {
            institutionService.updateInstitution(
                id, mappedInstitution.copy(
                    address = address,
                    contact = contact,
                    user = it
                )
            )
        }
    }
}