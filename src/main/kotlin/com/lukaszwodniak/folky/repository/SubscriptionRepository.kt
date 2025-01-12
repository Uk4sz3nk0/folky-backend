package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Subscription
import com.lukaszwodniak.folky.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * SubscriptionRepository
 *
 * Created on: 2025-05-11
 * @author Łukasz Wodniak
 */

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, Long> {

    fun findAllByUser(user: User): List<Subscription>
    fun existsByUserAndDancingTeam(user: User, dancingTeam: DancingTeam): Boolean
    fun findByUserAndDancingTeam(user: User, dancingTeam: DancingTeam): Optional<Subscription>
}