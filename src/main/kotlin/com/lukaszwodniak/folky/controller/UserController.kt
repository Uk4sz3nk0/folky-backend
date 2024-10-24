package com.lukaszwodniak.folky.controller

import com.lukaszwodniak.folky.rest.specification.models.DancingTeamDto
import com.lukaszwodniak.folky.rest.specification.models.UserDto
import com.lukaszwodniak.folky.rest.user.specification.api.UserApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * UserController
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@RestController
class UserController : UserApi {

    override fun addUser(body: UserDto?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: Long?): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

    override fun editUser(body: UserDto?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getUserById(id: Long?): ResponseEntity<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getUserSubscriptions(): ResponseEntity<MutableList<DancingTeamDto>> {
        TODO("Not yet implemented")
    }
}