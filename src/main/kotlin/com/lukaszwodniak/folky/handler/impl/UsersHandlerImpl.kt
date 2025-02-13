package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.UsersHandler
import com.lukaszwodniak.folky.mapper.UserMapper
import com.lukaszwodniak.folky.rest.specification.models.UserDataDto
import com.lukaszwodniak.folky.service.users.UserService
import org.springframework.stereotype.Service

/**
 * UsersHandlerImpl
 *
 * Created on: 2025-02-09
 * @author Łukasz Wodniak
 */

@Service
class UsersHandlerImpl(
    private val userService: UserService,
) : UsersHandler {

    override fun getUserById(id: Long): UserDataDto? {
        val user = userService.getUserById(id)
        return user?.let {
            UserMapper.INSTANCE.mapToData(it)
        }
    }

    override fun editUser(userDataDto: UserDataDto): UserDataDto? {
        val updatedUser = userService.editUser(UserMapper.INSTANCE.mapData(userDataDto))
        return updatedUser?.let { UserMapper.INSTANCE.mapToData(it) }
    }
}