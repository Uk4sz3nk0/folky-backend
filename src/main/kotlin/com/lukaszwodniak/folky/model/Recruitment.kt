package com.lukaszwodniak.folky.model

import jakarta.persistence.*
import java.util.*

/**
 * Recruitment
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "recruitments")
data class Recruitment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var startDate: Date,
    var endDate: Date,
    var description: String,
    @ElementCollection
    @CollectionTable(name = "recruitments", joinColumns = [JoinColumn(name = "recruitment_id")])
    @Column(name = "requirements")
    var requirements: MutableList<String>,
    var isOpened: Boolean,
    var isEndedByTime: Boolean,
    var isEndedBeforeEndTime: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dancing_team_id", nullable = false)
    var dancingTeam: DancingTeam,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recruitment")
    var recruitmentRequests: MutableList<RecruitmentRequest>
)
