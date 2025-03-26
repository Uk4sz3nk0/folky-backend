package com.lukaszwodniak.folky.model

import jakarta.persistence.*

/**
 * SocialMedia
 *
 * Created on: 2025-02-04
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "social_media")
data class SocialMedia(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @JoinColumn(name = "dancing_team_id")
    @OneToOne(fetch = FetchType.LAZY)
    var dancingTeam: DancingTeam?,
    val facebookUrl: String?,
    val instagramUrl: String?,
    val youtubeUrl: String?,
    val twitterUrl: String?,
    @Column(name = "tiktok_url")
    val tikTokUrl: String?,
)
