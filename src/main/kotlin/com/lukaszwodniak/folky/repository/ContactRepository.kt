package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Contact
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * ContactRepository
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@Repository
interface ContactRepository : JpaRepository<Contact, Long>