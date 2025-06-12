package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

/**
 * PeopleRepository
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Repository
interface PeopleRepository : JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    fun findAllByDancingTeam(dancingTeam: DancingTeam): List<Person>
}