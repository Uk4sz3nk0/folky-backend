package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * MusicInstrument
 * Created on: 2024-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Entity
@Table(name = "music_instruments")
data class MusicInstrument(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    @ManyToOne
    @JoinColumn(name = "type_id")
    val type: MusicInstrumentType? = null
)
