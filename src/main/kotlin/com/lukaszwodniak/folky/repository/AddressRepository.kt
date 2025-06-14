package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Address
import org.springframework.data.jpa.repository.JpaRepository

/**
 * AddressRepository
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

interface AddressRepository : JpaRepository<Address, Long>