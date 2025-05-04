package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.enums.EventConnectionType
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Event
import com.lukaszwodniak.folky.model.EventTeamInstitution
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * EventTeamInstitutionRepository
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@Repository
interface EventTeamInstitutionRepository : JpaRepository<EventTeamInstitution, Long>,
    JpaSpecificationExecutor<EventTeamInstitution> {

    @Query(
        """
        SELECT eti.team FROM EventTeamInstitution eti
        WHERE eti.event.id = :eventId
        AND eti.connectionType in (:connectionTypes)
    """
    )
    fun findAllParticipantsByEvent(
        @Param("eventId") eventId: Long,
        @Param("connectionTypes") connectionTypes: List<EventConnectionType>
    ): List<DancingTeam>

    fun findAllByEventIdInAndTeamId(eventIds: List<Long>, teamId: Long): List<EventTeamInstitution>
    fun findAllByEventAndTeam(event: Event, team: DancingTeam): List<EventTeamInstitution>

    @Modifying
    @Transactional
    @Query("DELETE FROM EventTeamInstitution eti WHERE eti.team = :team AND eti.event = :event AND eti.connectionType = :connectionType")
    fun deleteByTeamAndEventAndConnectionType(
        @Param("team") team: DancingTeam,
        @Param("event") event: Event,
        @Param("connectionType") connectionType: EventConnectionType
    )

    @Modifying
    @Transactional
    @Query("DELETE FROM EventTeamInstitution eti WHERE eti.event = :event")
    fun deleteByEvent(@Param("event") event: Event)

    fun existsByTeamAndEventAndConnectionType(
        team: DancingTeam,
        event: Event,
        connectionType: EventConnectionType
    ): Boolean

    fun findByEventAndConnectionType(event: Event, connectionType: EventConnectionType): Optional<EventTeamInstitution>

}