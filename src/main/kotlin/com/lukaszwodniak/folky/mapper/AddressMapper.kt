package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Address
import com.lukaszwodniak.folky.rest.address.specification.models.AddressDto
import com.lukaszwodniak.folky.rest.address.specification.models.PagedAddressesDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * AddressMapper
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Mapper
interface AddressMapper {

    fun mapToDto(address: Address): AddressDto
    fun mapFromDto(addressDto: AddressDto): Address

    fun mapToDtoList(addresses: List<Address>): List<AddressDto>

    fun mapToPaged(page: Page<Address>): PagedAddressesDto

    companion object {
        val INSTANCE: AddressMapper = Mappers.getMapper(AddressMapper::class.java)
    }
}