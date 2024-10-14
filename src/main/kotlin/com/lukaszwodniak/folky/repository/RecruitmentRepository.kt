package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Recruitment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * RecruitmentRepository
 *
 * Created on: 2024-10-19
 * @author Łukasz Wodniak
 */

@Repository
interface RecruitmentRepository : JpaRepository<Recruitment, Long> {
}