package com.lukaszwodniak.folky.service.people.impl

import com.lukaszwodniak.folky.enums.PersonPosition
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.Person
import com.lukaszwodniak.folky.repository.DancingTeamRepository
import com.lukaszwodniak.folky.repository.PeopleRepository
import com.lukaszwodniak.folky.service.people.PeopleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

/**
 * PeopleService
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Service
class PeopleServiceImpl(
    private val peopleRepository: PeopleRepository,
    private val dancingTeamRepository: DancingTeamRepository
) : PeopleService {

    override fun addPerson(person: Person) {
        peopleRepository.save(person)
    }

    override fun deletePerson(person: Person) {
        peopleRepository.delete(person)
    }

    override fun getPerson(id: Long): Person {
        return peopleRepository.findById(id).orElseThrow { NoSuchElementException("Person with id $id not found") }
    }

    override fun getPeople(pageRequest: PageRequest): Page<Person> {
        val pagination = pageRequest.withSort(Sort.by(Sort.Direction.DESC, "id"))
        return peopleRepository.findAll(pagination)
    }

    override fun updatePerson(existingPerson: Person, newPersonData: Person) {
        val updated = existingPerson.copy(
            firstName = newPersonData.firstName,
            lastName = newPersonData.lastName,
            positions = newPersonData.positions
        )
        peopleRepository.save(updated)
    }

    override fun updatedPeople(people: List<Person>, dancingTeam: DancingTeam?): List<Person> {
        val peopleWithoutPositions = people.map { it.copy(positions = mutableListOf()) }
        val savedPeople = peopleRepository.saveAllAndFlush(peopleWithoutPositions)

        val peopleWithPositions = savedPeople.mapIndexed { index, savedPerson ->
            val originalPerson = people[index]
            val updatedPositions = originalPerson.positions.map {
                it.copy(person = savedPerson)
            }.toMutableList()
            savedPerson.copy(positions = updatedPositions)
        }

        val updatedPeople = peopleRepository.saveAllAndFlush(peopleWithPositions)
        dancingTeam?.let { countTeamPeople(it) }
        return updatedPeople
    }

    private fun countTeamPeople(dancingTeam: DancingTeam) {
        val people = peopleRepository.findAllByDancingTeam(dancingTeam)
        val (dancers, musicians) = people.fold(0 to 0) { acc, person ->
            val positions = person.positions.map { it.position }.toSet()
            val dancerCount = if (PersonPosition.DANCER in positions) 1 else 0
            val musicianCount = if (PersonPosition.MUSICIAN in positions) 1 else 0
            (acc.first + dancerCount) to (acc.second + musicianCount)
        }
        val updatedTeam = dancingTeam.copy(dancersAmount = dancers, musiciansAmount = musicians)
        dancingTeamRepository.save(updatedTeam)
    }
}