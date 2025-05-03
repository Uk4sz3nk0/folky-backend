package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.Max

/**
 * Institution
 *
 * Created on: 2025-05-03
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "institutions")
data class Institution(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Max(150)
    val name: String,
    val description: String,
    val establishedYear: Int,
    @Max(300)
    val website: String,
    val filesUUID: UUID,
    @Max(255)
    val logo: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    val address: Address,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    val contact: Contact,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
)
