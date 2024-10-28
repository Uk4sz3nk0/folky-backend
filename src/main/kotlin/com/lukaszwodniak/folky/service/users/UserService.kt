package com.lukaszwodniak.folky.service.users

import com.google.firebase.auth.UserRecord
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.records.RegisterUserRequest

/**
 * UserService
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

interface UserService {

    fun registerUser(registerRequest: RegisterUserRequest)
    fun getUserFromContext(): User?
    fun getFirebaseUserFromContext(): UserRecord
}