package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.EventType
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.Max

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
    val id: Long,
    @Max(150)
    val name: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    @OneToOne
    @JoinColumn(name = "address_id")
    val address: Address,
    val ticketPrice: Double,
    @Enumerated(EnumType.STRING)
    val type: EventType,
    val filesUUID: UUID,
    @Max(200)
    val poster: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
