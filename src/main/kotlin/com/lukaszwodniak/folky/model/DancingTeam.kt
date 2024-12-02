package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import java.time.LocalDate
import java.util.*

/**
 * DancingTeam
 * Created on: 2024-07-31
 * @author Łukasz Wodniak
 */

@Entity
@Getter
@Setter
@Table(name = "dancing_teams")
data class DancingTeam(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    @Column(name = "files_uuid")
    val filesUUID: UUID,
    var description: String? = "",
    var creationDate: LocalDate = LocalDate.of(1970, 1, 1),
    @OneToOne
    @JoinColumn(name = "director_id")
    var director: User? = null,
    @ManyToOne
    @JoinColumn(name = "region_id")
    var region: Region? = null,
    var city: String? = "",
    var street: String? = "",
    var homeNumber: Int? = 0,
    var flatNumber: Int? = 0,
    var zipCode: String? = null,
    @ManyToMany
    @JoinTable(
        name = "dancing_team_dances",
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "dance_id")]
    )
    var dances: MutableList<Dance>? = mutableListOf(),
    @ManyToMany
    @JoinTable(
        name = "dancing_team_dancers",
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "dancer_id")]
    )
    var dancers: MutableList<User>? = mutableListOf(),
    @ManyToMany
    @JoinTable(
        joinColumns = [JoinColumn(name = "team_id")],
        inverseJoinColumns = [JoinColumn(name = "musician_id")]
    )
    var musicians: MutableList<User>? = mutableListOf(),
    var directoryUuid: UUID? = null,
    var logoFilename: String? = null,
    var bannerFilename: String? = null,
    var isRecruitmentOpened: Boolean? = false,
    @OneToMany(mappedBy = "dancingTeam", fetch = FetchType.LAZY)
    var recruitments: MutableList<Recruitment>? = mutableListOf(),
    @ManyToMany(mappedBy = "subscribedTeams", fetch = FetchType.LAZY)
    val subscribers: MutableSet<User>? = mutableSetOf(),
    @OneToMany(mappedBy = "dancingTeam")
    val userRoles: List<UserRole>? = emptyList(),
    @OneToOne
    @JoinColumn(name = "account_user_id")
    val accountUser: User? = null
)
