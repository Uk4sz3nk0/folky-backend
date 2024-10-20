package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Recruitment
import com.lukaszwodniak.folky.model.RecruitmentRequest
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentDto
import com.lukaszwodniak.folky.rest.specification.models.RecruitmentRequestDto
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

/**
 * RecruitmentMapper
 *
 * Created on: 2024-10-18
 * @author Łukasz Wodniak
 */

@Mapper
interface RecruitmentMapper {

    fun mapRecruitment(recruitment: Recruitment): RecruitmentDto
    fun mapRecruitmentDto(recruitmentDto: RecruitmentDto): Recruitment
    fun mapRecruitmentRequest(recruitmentRequest: RecruitmentRequest): RecruitmentRequestDto
    fun mapRecruitmentRequestDto(request: RecruitmentRequestDto): RecruitmentRequest

    fun mapRecruitments(recruitments: MutableList<Recruitment>): MutableList<RecruitmentDto>
    fun mapRecruitmentsDto(recruitments: MutableList<RecruitmentDto>): MutableList<Recruitment>
    fun mapRecruitmentRequests(requests: MutableList<RecruitmentRequest>): MutableList<RecruitmentRequestDto>
    fun mapRecruitmentRequestsDto(requests: MutableList<RecruitmentRequestDto>): MutableList<RecruitmentRequest>

    companion object {
        val INSTANCE: RecruitmentMapper = Mappers.getMapper(RecruitmentMapper::class.java)
    }
}