package com.lukaszwodniak.folky.mapper

import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.rest.specification.models.UserDto
import org.mapstruct.Mapper
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

    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}