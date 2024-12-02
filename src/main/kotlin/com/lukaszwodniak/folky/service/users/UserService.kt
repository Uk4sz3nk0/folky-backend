package com.lukaszwodniak.folky.service.users

import com.google.firebase.auth.UserRecord
import com.lukaszwodniak.folky.enums.UserType
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.records.RegisterDancingTeamUserRequest
import com.lukaszwodniak.folky.records.RegisterUserRequest

/**
 * UserService
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

interface UserService {

    fun registerUser(registerRequest: RegisterUserRequest, type: UserType = UserType.NORMAL): User?
    fun registerDancingTeamUser(registerRequest: RegisterDancingTeamUserRequest)
    fun getUserFromContext(): User?
    fun getFirebaseUserFromContext(): UserRecord
    fun getUserByEmail(email: String): User?
}