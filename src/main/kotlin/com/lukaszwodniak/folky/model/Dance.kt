package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * Dance
 * Created on: 2024-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Entity
@Table(name = "dances")
data class Dance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var locale: String,
    var name: String
)
