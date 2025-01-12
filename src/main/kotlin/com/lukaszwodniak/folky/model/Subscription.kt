package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * Subscription
 *
 * Created on: 2025-01-11
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "subscriptions")
data class Subscription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "dancing_team_id", nullable = false)
    val dancingTeam: DancingTeam
)