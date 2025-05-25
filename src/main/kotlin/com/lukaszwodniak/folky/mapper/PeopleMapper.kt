package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.Person
import com.lukaszwodniak.folky.model.PersonPosition
import com.lukaszwodniak.folky.rest.people.specification.models.PagedPeopleDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonDto
import com.lukaszwodniak.folky.rest.people.specification.models.PersonPositionDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * PeopleMapper
 *
 * Created on: 2025-05-17
 * @author Łukasz Wodniak
 */

@Mapper
interface PeopleMapper {

    fun mapFromDto(person: PersonDto): Person
    @Mapping(source = "dancingTeam.id", target = "dancingTeamId")
    fun mapToDto(person: Person): PersonDto

    fun mapToDtoList(people: List<Person>): List<PersonDto>

    fun mapToPageable(people: Page<Person>): PagedPeopleDto

    fun mapPositionFromDto(position: PersonPositionDto): PersonPosition
    fun mapPositionToDto(position: PersonPosition): PersonPositionDto
    fun mapPositionsToDto(positions: List<PersonPosition>): List<PersonPositionDto>
    fun mapPositionsFromDto(positions: List<PersonPositionDto>): List<PersonPosition>

    companion object {
        val INSTANCE: PeopleMapper = Mappers.getMapper(PeopleMapper::class.java)
    }
}