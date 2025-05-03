package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.AddressHandler
import com.lukaszwodniak.folky.mapper.AddressMapper
import com.lukaszwodniak.folky.rest.address.specification.models.AddressDto
import com.lukaszwodniak.folky.rest.address.specification.models.PagedAddressesDto
import com.lukaszwodniak.folky.service.address.AddressService
import org.springframework.stereotype.Service

/**
 * AddressHandlerImpl
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Service
class AddressHandlerImpl(
    private val addressService: AddressService,
) : AddressHandler {

    override fun handleAddAddress(address: AddressDto): Long {
        val mappedAddress = AddressMapper.INSTANCE.mapFromDto(address)
        return addressService.addAddress(mappedAddress)
    }

    override fun handleDeleteAddress(id: Long) {
        addressService.deleteAddress(id)
    }

    override fun handleGetAddress(id: Long): AddressDto {
        val address = addressService.getAddress(id)
        return AddressMapper.INSTANCE.mapToDto(address)
    }

    override fun handleGetAddresses(page: Int, size: Int): PagedAddressesDto {
        val addresses = addressService.getAddresses(page, size)
        return AddressMapper.INSTANCE.mapToPaged(addresses)
    }

    override fun handleUpdateAddress(id: Long, address: AddressDto) {
        val mappedAddress = AddressMapper.INSTANCE.mapFromDto(address)
        addressService.updateAddress(id, mappedAddress)
    }


}