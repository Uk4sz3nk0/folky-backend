package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * MusicInstrumentType
 * Created on: 2024-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Entity
@Table(name = "music_instruments_types")
data class MusicInstrumentType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    @OneToMany(mappedBy = "type")
    val instruments: List<MusicInstrument> = emptyList()
)
