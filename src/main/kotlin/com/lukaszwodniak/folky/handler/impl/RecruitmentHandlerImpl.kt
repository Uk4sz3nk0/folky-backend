package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.enums.RecruitmentRequestType
import com.lukaszwodniak.folky.error.NoSuchDancingTeamException
import com.lukaszwodniak.folky.error.recruitment.NoSuchRecruitmentException
import com.lukaszwodniak.folky.error.recruitment.NoSuchRecruitmentRequestException
import com.lukaszwodniak.folky.handler.RecruitmentHandler
import com.lukaszwodniak.folky.mapper.RecruitmentMapper
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.RecruitmentRepository
import com.lukaszwodniak.folky.repository.RecruitmentRequestRepository
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentDto
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentRequestDto
import com.lukaszwodniak.folky.service.recruitment.RecruitmentService
import org.springframework.stereotype.Service

/**
 * RecruitmentHandlerImpl
 *
 * Created on: 2024-10-18
 * @author Łukasz Wodniak
 */

@Service
class RecruitmentHandlerImpl(
    val dancingTeamRepository: DancingTeamRepository,
    val recruitmentRepository: RecruitmentRepository,
    val recruitmentRequestRepository: RecruitmentRequestRepository,
    val recruitmentService: RecruitmentService
) : RecruitmentHandler {

    override fun handleCreateRecruitment(recruitment: RecruitmentDto) {
        val dancingTeamId = recruitment.dancingTeamId
        val dancingTeam =
            dancingTeamRepository.findById(dancingTeamId).orElseThrow { NoSuchDancingTeamException(dancingTeamId) }
        val mappedRecruitment = RecruitmentMapper.INSTANCE.mapRecruitmentDto(recruitment)
        recruitmentService.createRecruitment(mappedRecruitment, dancingTeam)
    }

    override fun handleCreateRecruitmentRequest(recruitmentRequest: RecruitmentRequestDto) {
        val recruitmentId = recruitmentRequest.recruitmentId
        val recruitment =
            recruitmentRepository.findById(recruitmentId).orElseThrow { NoSuchRecruitmentException(recruitmentId) }
        val mappedRequest = RecruitmentMapper.INSTANCE.mapRecruitmentRequestDto(recruitmentRequest)
        recruitmentService.createRecruitmentRequest(mappedRequest, recruitment)
    }

    override fun handleDeleteRecruitmentRequest(id: Long) {
        recruitmentRepository.deleteById(id)
    }

    override fun handleEditRecruitment(recruitment: RecruitmentDto) {
        val mappedRecruitment = RecruitmentMapper.INSTANCE.mapRecruitmentDto(recruitment)
        recruitmentService.editRecruitment(mappedRecruitment)
    }

    override fun handleEditRecruitmentRequest(recruitmentRequest: RecruitmentRequestDto) {
        val mappedRequest = RecruitmentMapper.INSTANCE.mapRecruitmentRequestDto(recruitmentRequest)
        recruitmentService.editRecruitmentRequestRequest(mappedRequest)
    }

    override fun handleGetDancingTeamRecruitmentRequests(
        teamId: Long,
        recruitmentId: Long,
        requestType: String
    ): MutableList<RecruitmentRequestDto> {
        // TODO: Check usability this method
        val dancingTeam = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        val requests =
            recruitmentService.getDancingTeamRequestsByType(dancingTeam, RecruitmentRequestType.valueOf(requestType))
        return RecruitmentMapper.INSTANCE.mapRecruitmentRequests(requests)
    }

    override fun handleGetRecruitment(id: Long): RecruitmentDto {
        val recruitment = recruitmentRepository.findById(id)
            .orElseThrow { NoSuchRecruitmentException(id) }
        return RecruitmentMapper.INSTANCE.mapRecruitment(recruitment)
    }

    override fun handleGetRecruitmentRequest(id: Long): RecruitmentRequestDto {
        val request =
            recruitmentRequestRepository.findById(id).orElseThrow { NoSuchRecruitmentRequestException(id) }
        return RecruitmentMapper.INSTANCE.mapRecruitmentRequest(request)
    }

    override fun handleGetRecruitmentRequests(recruitmentId: Long): MutableList<RecruitmentRequestDto> {
        val recruitment = recruitmentRepository.findById(recruitmentId)
            .orElseThrow { NoSuchRecruitmentException(recruitmentId) }
        return RecruitmentMapper.INSTANCE.mapRecruitmentRequests(recruitment.recruitmentRequests)
    }

    override fun handleGetAllRecruitmentRequests(): MutableList<RecruitmentRequestDto> {
        val recruitmentRequests = recruitmentRequestRepository.findAll()
        return RecruitmentMapper.INSTANCE.mapRecruitmentRequests(recruitmentRequests)
    }

    override fun handleGetRecruitments(): MutableList<RecruitmentDto> {
        val recruitments = recruitmentRepository.findAll()
        return RecruitmentMapper.INSTANCE.mapRecruitments(recruitments)
    }

    override fun handleGetTeamRecruitments(teamId: Long): MutableList<RecruitmentDto> {
        val team = dancingTeamRepository.findById(teamId).orElseThrow { NoSuchDancingTeamException(teamId) }
        return RecruitmentMapper.INSTANCE.mapRecruitments(team?.recruitments ?: mutableListOf())
    }

    override fun handleGetUserRequests(requestType: String): MutableList<RecruitmentRequestDto> {
        // TODO: Implement getting user from context when Spring Security will be ready
        return mutableListOf()
    }
}