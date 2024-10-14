package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.RecruitmentHandler
import com.lukaszwodniak.folky.rest.recruitment.specification.api.RecruitmentApi
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentDto
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * RecruitmentController
 *
 * Created on: 2024-10-17
 * @author Łukasz Wodniak
 */

@RestController
class RecruitmentController(
    val recruitmentHandler: RecruitmentHandler,
) : RecruitmentApi {

    @EndpointLogger
    override fun createRecruitment(recruitment: RecruitmentDto?): ResponseEntity<Void> {
        recruitment?.let { recruitmentHandler.handleCreateRecruitment(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun createRecruitmentRequest(recruitmentRequest: RecruitmentRequestDto?): ResponseEntity<Void> {
        recruitmentRequest?.let { recruitmentHandler.handleCreateRecruitmentRequest(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deleteRecruitmentRequest(id: Long?): ResponseEntity<Void> {
        id?.let { recruitmentHandler.handleDeleteRecruitmentRequest(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun editRecruitment(recruitment: RecruitmentDto?): ResponseEntity<Void> {
        recruitment?.let { recruitmentHandler.handleEditRecruitment(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun editRecruitmentRequest(recruitmentRequest: RecruitmentRequestDto?): ResponseEntity<Void> {
        recruitmentRequest?.let { recruitmentHandler.handleEditRecruitmentRequest(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getDancingTeamRecruitmentRequests(
        teamId: Long?,
        recruitmentId: Long?,
        requestType: String?
    ): ResponseEntity<MutableList<RecruitmentRequestDto>> {
        return ResponseEntity.ok().body(
            teamId?.let { teamIdentification ->
                requestType?.let { requestType ->
                    recruitmentId?.let { recruitmentId ->
                        recruitmentHandler.handleGetDancingTeamRecruitmentRequests(
                            teamIdentification,
                            recruitmentId,
                            requestType
                        )
                    }
                }
            }
        )
    }

    @EndpointLogger
    override fun getRecruitment(id: Long?): ResponseEntity<RecruitmentDto> {
        return ResponseEntity.ok().body(id?.let { recruitmentHandler.handleGetRecruitment(it) })
    }

    @EndpointLogger
    override fun getRecruitmentRequest(id: Long?): ResponseEntity<RecruitmentRequestDto> {
        return ResponseEntity.ok().body(id?.let { recruitmentHandler.handleGetRecruitmentRequest(it) })
    }

    @EndpointLogger
    override fun getRecruitmentRequests(recruitmentId: Long?): ResponseEntity<MutableList<RecruitmentRequestDto>> {
        return ResponseEntity.ok()
            .body(recruitmentId?.let { recruitmentHandler.handleGetRecruitmentRequests(recruitmentId) })
    }

    @EndpointLogger
    override fun getAllRecruitmentRequests(): ResponseEntity<MutableList<RecruitmentRequestDto>> {
        return ResponseEntity.ok().body(recruitmentHandler.handleGetAllRecruitmentRequests())
    }

    @EndpointLogger
    override fun getRecruitments(): ResponseEntity<MutableList<RecruitmentDto>> {
        return ResponseEntity.ok().body(recruitmentHandler.handleGetRecruitments())
    }

    @EndpointLogger
    override fun getTeamRecruitments(teamId: Long?): ResponseEntity<MutableList<RecruitmentDto>> {
        return ResponseEntity.ok().body(teamId?.let { recruitmentHandler.handleGetTeamRecruitments(it) })
    }

    @EndpointLogger
    override fun getUserRequests(requestType: String?): ResponseEntity<MutableList<RecruitmentRequestDto>> {
        return ResponseEntity.ok().body(requestType?.let { recruitmentHandler.handleGetUserRequests(it) })
    }
}