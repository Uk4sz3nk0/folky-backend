package com.lukaszwodniak.folky.model

import com.lukaszwodniak.folky.enums.RecruitmentRequestState
import com.lukaszwodniak.folky.enums.RecruitmentRequestType
import jakarta.persistence.*

/**
 * RecruitmentRequest
 *
 * Created on: 2024-10-14
 * @author Łukasz Wodniak
 */

@Entity
@Table(name = "recruitment_requests")
data class RecruitmentRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var message: String,
    @Enumerated(EnumType.STRING)
    var state: RecruitmentRequestState,
    @Enumerated(EnumType.STRING)
    var type: RecruitmentRequestType,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    var recruitment: Recruitment
)