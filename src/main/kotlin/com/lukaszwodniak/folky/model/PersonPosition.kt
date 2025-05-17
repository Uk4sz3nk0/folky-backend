package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.PersonPosition
import jakarta.persistence.*
import java.time.LocalDate

/**
 * PersonPosition
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "people_positions")
data class PersonPosition(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "person_id")
    val person: Person? = null,
    @Enumerated(EnumType.STRING)
    val position: PersonPosition,
    val since: LocalDate? = null,
)
