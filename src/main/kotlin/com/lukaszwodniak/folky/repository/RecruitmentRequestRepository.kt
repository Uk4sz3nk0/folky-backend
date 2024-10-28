package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.RecruitmentRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * RecruitmentRequestRepository
 *
 * Created on: 2024-10-19
 * @author Łukasz Wodniak
 */

@Repository
interface RecruitmentRequestRepository : JpaRepository<RecruitmentRequest, Long> {
}