package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Institution
import com.lukaszwodniak.folky.rest.institution.specification.models.InstitutionDto
import com.lukaszwodniak.folky.rest.institution.specification.models.PagedInstitutionsDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * InstitutionMapper
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Mapper
interface InstitutionMapper {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "user.id", target = "userId")
    fun mapToDto(institution: Institution): InstitutionDto
    fun mapFromDto(dto: InstitutionDto): Institution

    fun mapToDtoList(institutions: List<Institution>): List<InstitutionDto>

    fun mapToPageable(institutions: Page<Institution>): PagedInstitutionsDto

    companion object {
        val INSTANCE: InstitutionMapper = Mappers.getMapper(InstitutionMapper::class.java)
    }
}