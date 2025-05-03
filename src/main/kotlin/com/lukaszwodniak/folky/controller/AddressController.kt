package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.AddressHandler
import com.lukaszwodniak.folky.rest.address.specification.api.AddressApi
import com.lukaszwodniak.folky.rest.address.specification.models.AddressDto
import com.lukaszwodniak.folky.rest.address.specification.models.PagedAddressesDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * AddressController
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@RestController
class AddressController(
    private val addressHandler: AddressHandler,
) : AddressApi {

    @EndpointLogger
    override fun addAddress(address: AddressDto?): ResponseEntity<Long> {
        val response = address?.let { addressHandler.handleAddAddress(it) } ?: -1
        return ResponseEntity.ok(response)
    }

    @EndpointLogger
    override fun deleteAddress(id: Long?): ResponseEntity<Void> {
        id?.let { addressHandler.handleDeleteAddress(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getAddress(id: Long?): ResponseEntity<AddressDto> {
        val address = id?.let { addressHandler.handleGetAddress(it) }
        return ResponseEntity.ok(address)
    }

    @EndpointLogger
    override fun getAddresses(page: Int?, size: Int?): ResponseEntity<PagedAddressesDto> {
        val addresses = addressHandler.handleGetAddresses(page ?: DEFAULT_PAGE, size ?: DEFAULT_SIZE)
        return ResponseEntity.ok(addresses)
    }

    @EndpointLogger
    override fun updateAddress(id: Long?, address: AddressDto?): ResponseEntity<Void> {
        id?.let { addressId ->
            address?.let {
                addressHandler.handleUpdateAddress(addressId, it)
            }
        }
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val DEFAULT_PAGE: Int = 0
        private const val DEFAULT_SIZE: Int = 10
    }
}