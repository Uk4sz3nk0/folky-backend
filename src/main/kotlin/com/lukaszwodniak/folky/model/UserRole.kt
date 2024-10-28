package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * UserRole - Entity that represents user - role - dancing team relation
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "user_role")
data class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    val role: Role,

    @ManyToOne
    @JoinColumn(name = "dancing_team_id", nullable = false)
    val dancingTeam: DancingTeam
)
