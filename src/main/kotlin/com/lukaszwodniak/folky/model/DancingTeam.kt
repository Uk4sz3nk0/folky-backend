package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

/**
 * DancingTeam
 * Created on: 2024-07-31
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "dancing_teams")
data class DancingTeam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    val filesUUID: UUID,
    var description: String,
    var creationDate: LocalDate,
    @OneToOne
    @JoinColumn(name = "director_id")
    var director: User,
    @ManyToOne
    @JoinColumn(name = "region_id")
    var region: Region,
    var city: String,
    var street: String,
    var homeNumber: Int,
    var flatNumber: Int,
    var zipCode: String,
    @ManyToMany
    @JoinTable(
        name = "dancing_team_dances",
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "dance_id")]
    )
    var dances: List<Dance>,
    @ManyToMany
    @JoinTable(
        name = "dancing_team_dancers",
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "dancer_id")]
    )
    var dancers: List<User>,
    @ManyToMany
    @JoinTable(
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "musician_id")]
    )
    var musicians: List<User>
)
