package com.lukaszwodniak.folky.service.address.impl

import com.lukaszwodniak.folky.error.NoSuchAddressException
import com.lukaszwodniak.folky.model.Address
import com.lukaszwodniak.folky.repository.AddressRepository
import com.lukaszwodniak.folky.service.address.AddressService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * AddressServiceImpl
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Service
class AddressServiceImpl(
    private val addressRepository: AddressRepository
) : AddressService {

    override fun addAddress(address: Address): Long {
        val saved = addressRepository.saveAndFlush(address)
        return saved.id!!
    }

    override fun deleteAddress(id: Long) {
        addressRepository.deleteById(id)
    }

    override fun getAddress(id: Long): Address {
        return addressRepository.findById(id).orElseThrow { NoSuchAddressException(id) }
    }

    override fun getAddresses(page: Int, size: Int): Page<Address> {
        val pageRequest = PageRequest.of(page, size)
        return addressRepository.findAll(pageRequest)
    }

    override fun updateAddress(id: Long, address: Address) {
        val existingAddress = addressRepository.findById(id).orElseThrow { NoSuchAddressException(id) }
        val editedAddress = existingAddress.copy(
            street = address.street,
            city = address.city,
            postalCode = address.postalCode,
            country = address.country,
            latitude = address.latitude,
            longitude = address.longitude,
        )
        addressRepository.saveAndFlush(editedAddress)
    }
}