package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import java.time.LocalDate

/**
 * User
 * Created on: 2024-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val fistName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val brithDate: LocalDate,
    val howLongDancing: Int,
    @ManyToMany
    @JoinTable(
        name = "user_dances",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "dance_id")]
    )
    val dances: List<Dance>,
    val howLongPlayingInstruments: Int,
    @ManyToMany
    @JoinTable(
        name = "user_instruments",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "music_instrument_id")]
    )
    val instruments: List<MusicInstrument>,
    @ManyToOne
    @JoinColumn(name = "role_id")
    val role: Role
)
