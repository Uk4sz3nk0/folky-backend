package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import javax.validation.constraints.Max

/**
 * Contact
 *
 * Created on: 2025-04-27
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "contacts")
data class Contact(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Max(20)
    val phoneNumber: String?,
    @Max(150)
    val email: String?
)