package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.EventConnectionType
import com.lukaszwodniak.folky.enums.EventType
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.Max
import kotlin.jvm.Transient

/**
 * Event
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "events")
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Max(150)
    val title: String,
    val description: String? = null,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    @OneToOne
    @JoinColumn(name = "address_id")
    val address: Address?,
    val ticketPrice: Double?,
    @Enumerated(EnumType.STRING)
    val type: EventType? = null,
    @Column(name = "files_uuid")
    val filesUUID: UUID? = null,
    @Max(200)
    val poster: String? = null,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    @Transient
    val connectionTypes: List<EventConnectionType>? = emptyList(),
    @OneToMany(mappedBy = "event")
    val eti: List<EventTeamInstitution>? = emptyList()
)
