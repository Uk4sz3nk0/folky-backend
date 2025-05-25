package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto

/**
 * PeopleHandler
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

interface PeopleHandler {

    fun handleAddPerson(person: PersonDto)
    fun handleDeletePerson(id: Long)
    fun handleGetPerson(id: Long): PersonDto
    fun handleGetPeople(page: Int, size: Int): PagedPeopleDto
    fun handleUpdatePerson(id: Long, person: PersonDto)
}