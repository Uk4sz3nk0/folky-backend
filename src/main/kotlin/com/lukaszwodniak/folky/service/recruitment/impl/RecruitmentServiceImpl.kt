package com.lukaszwodniak.folky.service.recruitment.impl

import com.lukaszwodniak.folky.enums.RecruitmentRequestType
import com.lukaszwodniak.folky.error.recruitment.NoSuchRecruitmentException
import com.lukaszwodniak.folky.error.recruitment.NoSuchRecruitmentRequestException
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Recruitment
import com.lukaszwodniak.folky.model.RecruitmentRequest
import com.lukaszwodniak.folky.repository.RecruitmentRepository
import com.lukaszwodniak.folky.repository.RecruitmentRequestRepository
import com.lukaszwodniak.folky.service.recruitment.RecruitmentService
import org.springframework.stereotype.Service

/**
 * RecruitmentServiceImpl
 *
 * Created on: 2024-10-20
 * @author Łukasz Wodniak
 */

@Service
class RecruitmentServiceImpl(
    val recruitmentRepository: RecruitmentRepository,
    val recruitmentRequestRepository: RecruitmentRequestRepository
) : RecruitmentService {

    override fun createRecruitment(recruitment: Recruitment, dancingTeam: DancingTeam) {
        val recruitmentToSave = recruitment.copy(dancingTeam = dancingTeam)
        recruitmentRepository.saveAndFlush(recruitmentToSave)
    }

    override fun editRecruitment(recruitment: Recruitment) {
        val existingRecruitment =
            recruitmentRepository.findById(recruitment.id).orElseThrow { NoSuchRecruitmentException(recruitment.id) }
        val updatedRecruitment = existingRecruitment.copy(
            startDate = recruitment.startDate,
            endDate = recruitment.endDate,
            description = recruitment.description,
            requirements = recruitment.requirements,
            isOpened = recruitment.isOpened,
            isEndedByTime = recruitment.isEndedByTime,
            isEndedBeforeEndTime = recruitment.isEndedBeforeEndTime,
        )
        recruitmentRepository.saveAndFlush(updatedRecruitment)
    }

    override fun createRecruitmentRequest(recruitmentRequest: RecruitmentRequest, recruitment: Recruitment) {
        val requestToSave = recruitmentRequest.copy(recruitment = recruitment)
        recruitmentRequestRepository.saveAndFlush(requestToSave)
    }

    override fun editRecruitmentRequestRequest(recruitmentRequest: RecruitmentRequest) {
        val existingRequest = recruitmentRequestRepository.findById(recruitmentRequest.id)
            .orElseThrow { NoSuchRecruitmentRequestException(recruitmentRequest.id) }
        val updatedRequest = existingRequest.copy(
            message = recruitmentRequest.message,
            type = recruitmentRequest.type,
            state = recruitmentRequest.state
        )
        recruitmentRequestRepository.saveAndFlush(updatedRequest)
    }

    override fun getDancingTeamRequestsByType(
        dancingTeam: DancingTeam,
        type: RecruitmentRequestType
    ): MutableList<RecruitmentRequest> {
        val dancingTeamRecruitments = dancingTeam.recruitments
        val dancingTeamRecruitmentRequests = dancingTeamRecruitments!!.filter { recruitment -> recruitment.isOpened }
            .flatMap { recruitment -> recruitment.recruitmentRequests }.toMutableList()
        val filteredRequests =
            dancingTeamRecruitmentRequests.filter { recruitmentRequest -> recruitmentRequest.type == type }
                .toMutableList()
        return filteredRequests
    }
}