package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.EventConnectionType
import jakarta.persistence.*

/**
 * EventTeamInstitution
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "event_team_institution")
data class EventTeamInstitution(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: DancingTeam,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    val institution: Institution,
    @Enumerated(EnumType.STRING)
    val connectionType: EventConnectionType,
)
