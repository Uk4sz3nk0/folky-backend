package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.address.specification.models.AddressDto
import com.lukaszwodniak.folky.rest.address.specification.models.PagedAddressesDto

/**
 * AddressHandler
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

interface AddressHandler {

    fun handleAddAddress(address: AddressDto): Long
    fun handleDeleteAddress(id: Long)
    fun handleGetAddress(id: Long): AddressDto
    fun handleGetAddresses(page: Int, size: Int): PagedAddressesDto
    fun handleUpdateAddress(id: Long, address: AddressDto)
}