package com.lukaszwodniak.folky.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.lukaszwodniak.folky.enums.UserType
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
    val id: Long?,
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var brithDate: LocalDate,
    var howLongDancing: Int,
    @ManyToMany
    @JoinTable(
        name = "user_dances",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "dance_id")]
    )
    var dances: MutableList<Dance>,
    var howLongPlayingInstruments: Int,
    @ManyToMany
    @JoinTable(
        name = "user_instruments",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "music_instrument_id")]
    )
    var instruments: MutableList<MusicInstrument>,
    @OneToMany(mappedBy = "user")
    val userRoles: List<UserRole>? = emptyList(),
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val recruitmentRequests: MutableList<RecruitmentRequest>,
    var preferredLanguage: String,
    var wantReceivePushNotifications: Boolean,
    var wantReceiveEmailNotifications: Boolean,
    @OneToMany
    @JoinColumn(name = "user_id")
    val deviceTokens: MutableSet<DeviceToken>,
    val uid: String,
    @Enumerated(EnumType.STRING)
    val userType: UserType,
    @OneToMany(mappedBy = "accountUser")
    val dancingTeams: MutableList<DancingTeam> = mutableListOf()
)
