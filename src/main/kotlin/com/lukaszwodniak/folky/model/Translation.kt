package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * Translation
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "translations_dictionary")
data class Translation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val language: String,
    var specifier: String? = null,
    var value: String
)
