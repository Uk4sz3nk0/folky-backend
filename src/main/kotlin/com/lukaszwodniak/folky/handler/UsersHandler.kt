package com.lukaszwodniak.folky.handler

import com.lukaszwodniak.folky.rest.specification.models.UserDataDto

/**
 * UsersHandler
 *
 * Created on: 2025-02-09
 * @author Łukasz Wodniak
 */

interface UsersHandler {

    fun getUserById(id: Long): UserDataDto?
    fun editUser(userDataDto: UserDataDto): UserDataDto?
}