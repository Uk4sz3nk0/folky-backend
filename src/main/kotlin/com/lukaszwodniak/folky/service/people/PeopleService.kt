package com.lukaszwodniak.folky.service.people

import com.lukaszwodniak.folky.model.Achievement
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

/**
 * PeopleService
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

interface PeopleService {

    fun addPerson(person: Person)
    fun deletePerson(person: Person)
    fun getPerson(id: Long): Person
    fun getPeople(pageRequest: PageRequest): Page<Person>
    fun updatePerson(existingPerson: Person, newPersonData: Person)
    fun updatedPeople(people: List<Person>, dancingTeam: DancingTeam? = null): List<Person>
}