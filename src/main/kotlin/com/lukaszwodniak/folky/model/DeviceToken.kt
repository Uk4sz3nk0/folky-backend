package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.DeviceType
import jakarta.persistence.*
import java.time.Instant

/**
 * DeviceToken
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "device_tokens")
data class DeviceToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val token: String,
    @Enumerated(EnumType.STRING)
    var deviceType: DeviceType,
    val createdAt: Instant,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
)
