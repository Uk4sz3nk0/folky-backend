package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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
    var homeNumber: String? = "",
    var flatNumber: Int? = 0,
    var zipCode: String? = null,
    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
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
    var logoFilename: String? = null,
    var bannerFilename: String? = null,
    var isRecruitmentOpened: Boolean? = false,
    @OneToMany(mappedBy = "dancingTeam", fetch = FetchType.LAZY)
    var recruitments: MutableList<Recruitment>? = mutableListOf(),
    @OneToMany(mappedBy = "dancingTeam")
    val userRoles: List<UserRole>? = emptyList(),
    @ManyToOne
    @JoinColumn(name = "account_user_id")
    var accountUser: User? = null,
    @OneToOne(mappedBy = "dancingTeam", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    var socialMedia: SocialMedia?,
    val dancersAmount: Int = 0,
    val musiciansAmount: Int = 0,
    @OneToOne
    @JoinColumn(name = "contact_id")
    val contact: Contact? = null,
    @OneToMany(mappedBy = "dancingTeam", fetch = FetchType.LAZY)
    val achievements: MutableList<Achievement> = mutableListOf()
)
