package com.lukaszwodniak.folky.service.address

import com.lukaszwodniak.folky.model.Address
import org.springframework.data.domain.Page

/**
 * AddressService
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

interface AddressService {

    fun addAddress(address: Address): Long
    fun deleteAddress(id: Long)
    fun getAddress(id: Long): Address
    fun getAddresses(page: Int, size: Int): Page<Address>
    fun updateAddress(id: Long, address: Address)
}