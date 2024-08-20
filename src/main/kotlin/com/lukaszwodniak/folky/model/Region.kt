package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * Region
 * Created on: 2024-07-27
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Entity
@Table(name = "regions")
data class Region(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var locale: String,
    var name: String,
)