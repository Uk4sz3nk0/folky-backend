package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.model.UserRole
import com.lukaszwodniak.folky.records.UserData
import com.lukaszwodniak.folky.rest.specification.models.PageUserDto
import com.lukaszwodniak.folky.rest.specification.models.UserDataDto
import com.lukaszwodniak.folky.rest.specification.models.UserDto
import com.lukaszwodniak.folky.rest.specification.models.UserRoleDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page

/**
 * UserMapper
 * Created on: 2024-08-12
 * @author Łukasz Wodniak
 */

@Mapper
interface UserMapper {

    fun map(user: User): UserDto
    fun mapToData(user: User): UserDataDto?
    fun mapFromData(dto: UserDataDto): User
    fun mapData(user: UserDataDto): UserData

    fun map(users: List<User>): MutableList<UserDto>
    fun mapUserData(userData: User): UserDataDto

    fun mapToPage(pageUser: Page<User>): PageUserDto

    @Mapping(source = "role.name", target = "name")
    @Mapping(source = "dancingTeam.id", target = "dancingTeamId")
    fun mapUserRole(userRole: UserRole): UserRoleDto

    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}