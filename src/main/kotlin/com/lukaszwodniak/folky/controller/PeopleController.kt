package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.annotations.endpointLogger.EndpointLogger
import com.lukaszwodniak.folky.handler.PeopleHandler
import com.lukaszwodniak.folky.rest.people.specification.api.PeopleApi
import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * PeopleController
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@RestController
class PeopleController(
    private val peopleHandler: PeopleHandler,
) : PeopleApi {

    @EndpointLogger
    override fun addPerson(person: PersonDto?): ResponseEntity<Void> {
        person?.let { peopleHandler.handleAddPerson(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun deletePerson(id: Long?): ResponseEntity<Void> {
        id?.let { peopleHandler.handleDeletePerson(it) }
        return ResponseEntity.ok().build()
    }

    @EndpointLogger
    override fun getPeople(page: Int?, size: Int?): ResponseEntity<PagedPeopleDto> {
        val people = peopleHandler.handleGetPeople(
            page ?: ControllerCommons.DEFAULT_PAGE,
            size ?: ControllerCommons.DEFAULT_PAGE_SIZE
        )
        return ResponseEntity.ok(people)
    }

    @EndpointLogger
    override fun getPerson(id: Long?): ResponseEntity<PersonDto> {
        val person = id?.let { peopleHandler.handleGetPerson(it) }
        return ResponseEntity.ok(person)
    }

    @EndpointLogger
    override fun updatePerson(id: Long?, person: PersonDto?): ResponseEntity<Void> {
        person?.let { p ->
            id?.let {
                peopleHandler.handleUpdatePerson(it, p)
            }
        }
        return ResponseEntity.ok().build()
    }

}