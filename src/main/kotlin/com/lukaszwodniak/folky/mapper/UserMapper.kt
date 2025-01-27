package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.model.UserRole
import com.lukaszwodniak.folky.rest.specification.models.UserDataDto
import com.lukaszwodniak.folky.rest.specification.models.UserDto
import com.lukaszwodniak.folky.rest.specification.models.UserRoleDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

/**
 * UserMapper
 * Created on: 2024-08-12
 * @author Łukasz Wodniak
 */

@Mapper
interface UserMapper {

    fun map(user: User): UserDto

    fun map(users: List<User>): MutableList<UserDto>
    fun mapUserData(userData: User): UserDataDto
    @Mapping(source = "role.name", target = "name")
    @Mapping(source = "dancingTeam.id", target = "dancingTeamId")
    fun mapUserRole(userRole: UserRole): UserRoleDto

    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}