package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

/**
 * EventsRepository
 *
 * Created on: 2025-05-04
 * @author Łukasz Wodniak
 */

@Repository
interface EventsRepository : JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
}