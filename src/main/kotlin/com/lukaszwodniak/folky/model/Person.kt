package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import javax.validation.constraints.Max

/**
 * Person
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "people")
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Max(120)
    var firstName: String,
    @Max(120)
    var lastName: String,
    @OneToOne
    @JoinColumn(name = "dancing_team_id", referencedColumnName = "id")
    var dancingTeam: DancingTeam? = null,
    @OneToMany(mappedBy = "person", cascade = [CascadeType.ALL], orphanRemoval = true)
    val positions: MutableList<PersonPosition> = mutableListOf()
)