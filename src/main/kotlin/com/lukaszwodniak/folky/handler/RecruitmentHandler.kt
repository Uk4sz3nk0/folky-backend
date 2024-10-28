package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.RecruitmentDto
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentRequestDto

/**
 * RecruitmentHandler
 *
 * Created on: 2024-10-18
 * @author Łukasz Wodniak
 */

interface RecruitmentHandler {

    fun handleCreateRecruitment(recruitment: RecruitmentDto)
    fun handleCreateRecruitmentRequest(recruitmentRequest: RecruitmentRequestDto)
    fun handleDeleteRecruitmentRequest(id: Long)
    fun handleEditRecruitment(recruitment: RecruitmentDto)
    fun handleEditRecruitmentRequest(recruitmentRequest: RecruitmentRequestDto)
    fun handleGetDancingTeamRecruitmentRequests(
        teamId: Long,
        recruitmentId: Long,
        requestType: String
    ): MutableList<RecruitmentRequestDto>

    fun handleGetRecruitment(id: Long): RecruitmentDto
    fun handleGetRecruitmentRequest(id: Long): RecruitmentRequestDto
    fun handleGetRecruitmentRequests(recruitmentId: Long): MutableList<RecruitmentRequestDto>
    fun handleGetAllRecruitmentRequests(): MutableList<RecruitmentRequestDto>
    fun handleGetRecruitments(): MutableList<RecruitmentDto>
    fun handleGetTeamRecruitments(teamId: Long): MutableList<RecruitmentDto>
    fun handleGetUserRequests(requestType: String): MutableList<RecruitmentRequestDto>
}