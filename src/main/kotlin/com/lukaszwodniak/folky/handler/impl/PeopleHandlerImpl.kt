package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.PeopleHandler
import com.lukaszwodniak.folky.mapper.PeopleMapper
import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto
import com.lukaszwodniak.folky.service.people.PeopleService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

/**
 * PeopleHandler
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Service
class PeopleHandlerImpl(
    private val peopleService: PeopleService
) : PeopleHandler {

    override fun handleAddPerson(person: PersonDto) {
        val mappedPerson = PeopleMapper.INSTANCE.mapFromDto(person)
        peopleService.addPerson(mappedPerson)
    }

    override fun handleDeletePerson(id: Long) {
        val person = peopleService.getPerson(id)
        peopleService.deletePerson(person)
    }

    override fun handleGetPerson(id: Long): PersonDto {
        val person = peopleService.getPerson(id)
        return PeopleMapper.INSTANCE.mapToDto(person)
    }

    override fun handleGetPeople(page: Int, size: Int): PagedPeopleDto {
        val people = peopleService.getPeople(PageRequest.of(page, size))
        return PeopleMapper.INSTANCE.mapToPageable(people)
    }

    override fun handleUpdatePerson(id: Long, person: PersonDto) {
        val existingPerson = peopleService.getPerson(id)
        val mappedPerson = PeopleMapper.INSTANCE.mapFromDto(person)
        peopleService.updatePerson(existingPerson, mappedPerson)
    }
}