package com.lukaszwodniak.folky.service.recruitment

import com.lukaszwodniak.folky.enums.RecruitmentRequestType
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Recruitment
import com.lukaszwodniak.folky.model.RecruitmentRequest
import org.springframework.stereotype.Service

/**
 * RecruitmentService
 *
 * Created on: 2024-10-20
 * @author Łukasz Wodniak
 */

@Service
interface RecruitmentService {

    fun createRecruitment(recruitment: Recruitment, dancingTeam: DancingTeam)
    fun editRecruitment(recruitment: Recruitment)
    fun createRecruitmentRequest(recruitmentRequest: RecruitmentRequest, recruitment: Recruitment)
    fun editRecruitmentRequestRequest(recruitmentRequest: RecruitmentRequest)
    fun getDancingTeamRequestsByType(
        dancingTeam: DancingTeam,
        type: RecruitmentRequestType
    ): MutableList<RecruitmentRequest>
}