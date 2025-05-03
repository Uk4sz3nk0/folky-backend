package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import javax.validation.constraints.Max

/**
 * Address
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "addresses")
data class Address(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Max(150)
    val street: String,
    @Max(10)
    val postalCode: String,
    @Max(100)
    val city: String,
    @Max(100)
    val country: String,
    val latitude: Double? = null,
    val longitude: Double? = null
)
