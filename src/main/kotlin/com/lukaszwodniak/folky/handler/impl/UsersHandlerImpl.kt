package com.lukaszwodniak.folky.handler.impl

import com.lukaszwodniak.folky.handler.UsersHandler
import com.lukaszwodniak.folky.mapper.UserMapper
import com.lukaszwodniak.folky.rest.specification.models.PageUserDto
import com.lukaszwodniak.folky.rest.specification.models.UserDataDto
import com.lukaszwodniak.folky.service.users.UserService
import org.springframework.data.domain.PageRequest
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

    override fun editUser(id: Long, userDataDto: UserDataDto): UserDataDto? {
        val updateData = UserMapper.INSTANCE.mapData(userDataDto)
        val existingUser = userService.getUserById(id)
        val updatedUser = existingUser?.let { userService.editUser(it, updateData) }
        return updatedUser?.let { UserMapper.INSTANCE.mapToData(it) }
    }

    override fun handleGetUsers(page: Int, size: Int, phrase: String?): PageUserDto {
        val users = userService.getUsers(PageRequest.of(page, size), phrase)
        return UserMapper.INSTANCE.mapToPage(users)
    }
}