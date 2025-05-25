package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Achievement
import com.lukaszwodniak.folky.model.DancingTeam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * AchievementsRepository
 *
 * Created on: 2025-05-18
 * @author Łukasz Wodniak
 */

@Repository
interface AchievementsRepository : JpaRepository<Achievement, Long> {

    fun findByDancingTeam(dancingTeam: DancingTeam, page: Pageable): Page<Achievement>
}