package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.AchievementCategory
import com.lukaszwodniak.folky.enums.AchievementLevel
import jakarta.persistence.*
import java.time.LocalDate
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 * Achievement
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "achievements")
data class Achievement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Max(300)
    @NotNull
    @NotBlank
    val name: String,
    val date: LocalDate? = null,
    val year: Int? = null,
    @OneToOne
    @JoinColumn(name = "event_id")
    val event: Event? = null,
    @Max(120)
    @NotNull
    @NotBlank
    val city: String,
    val description: String? = null,
    @Max(250)
    @NotNull
    @NotBlank
    val organizer: String,
    @Enumerated(EnumType.STRING)
    val category: AchievementCategory,
    @Enumerated(EnumType.STRING)
    val level: AchievementLevel,
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "achievements_distinguished_people",
        joinColumns = [JoinColumn(name = "achievement_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    val distinguishedPeople: MutableList<Person> = mutableListOf(),
    @ManyToOne
    @JoinColumn(name = "dancing_team_id")
    val dancingTeam: DancingTeam? = null,
)
