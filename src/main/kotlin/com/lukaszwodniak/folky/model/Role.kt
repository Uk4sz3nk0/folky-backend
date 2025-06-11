package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * Role
 *
 * Created on: 2204-07-28
 * @author Łukasz Wodniak (lukasz.wodniak@student.up.krakow.pl)
 */

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    @OneToMany(mappedBy = "role")
    val userRoles: List<UserRole> = emptyList()
)
