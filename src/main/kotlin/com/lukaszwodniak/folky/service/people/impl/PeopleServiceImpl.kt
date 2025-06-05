package com.lukaszwodniak.folky.service.people.impl

import com.lukaszwodniak.folky.model.Person
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
    private val peopleRepository: PeopleRepository
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

    override fun updatedPeople(people: List<Person>): List<Person> {
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
        return updatedPeople
    }
}