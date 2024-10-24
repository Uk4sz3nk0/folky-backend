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
    var fistName: String,
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
    @ManyToOne
    @JoinColumn(name = "role_id")
    var role: Role,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    val recruitmentRequests: MutableList<RecruitmentRequest>,
    var preferredLanguage: String,
    var wantReceivePushNotifications: Boolean,
    var wantReceiveEmailNotifications: Boolean,
    @ManyToMany
    @JoinTable(
        name = "subscriptions",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "dancing_team_id")]
    )
    val subscribedTeams: MutableSet<DancingTeam>,
    @OneToMany
    @JoinColumn(name = "user_id")
    val tokens: MutableSet<DeviceToken>
)
