package com.lukaszwodniak.folky.service.users.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest
import com.lukaszwodniak.folky.error.users.EmailInUseException
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.records.RegisterUserRequest
import com.lukaszwodniak.folky.repository.UserRepository
import com.lukaszwodniak.folky.security.AuthenticatedUserIdProvider
import com.lukaszwodniak.folky.service.users.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * UserServiceImpl
 *
 * Created on: 2024-10-28
 * @author Łukasz Wodniak
 */

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth,
    private val authenticatedUserIdProvider: AuthenticatedUserIdProvider,
) : UserService {

    override fun registerUser(registerRequest: RegisterUserRequest) {
        val userUID = registerRequest.email?.let {
            registerRequest.password?.let { it1 ->
                createUserOnFirebase(
                    it,
                    it1
                )
            }
        }
        val newUser = userUID?.let { createNewUser(it, registerRequest) }
        if (newUser != null) {
            userRepository.save(newUser)
        }
    }

    override fun getUserFromContext(): User? {
        val userId = authenticatedUserIdProvider.userId
        return userRepository.findByUid(userId)
    }

    override fun getFirebaseUserFromContext(): UserRecord {
        val userId = authenticatedUserIdProvider.userId
        return firebaseAuth.getUser(userId)
    }

    private fun createUserOnFirebase(email: String, password: String): String {
        val userCreateRequest = CreateRequest()
        userCreateRequest.setEmail(email)
        userCreateRequest.setPassword(password)
        userCreateRequest.setEmailVerified(true)

        try {
            val userRecord = firebaseAuth.createUser(userCreateRequest)
            return userRecord.uid
        } catch (exception: FirebaseAuthException) {
            if (exception.message?.contains(DUPLICATE_ACCOUNT_ERROR) == true) {
                throw EmailInUseException("Account with given email-id already exists")
            }
            throw exception;
        }
    }

    private fun createNewUser(uid: String, request: RegisterUserRequest): User {
        return User(
            null,
            request.firstName ?: "",
            request.lastName ?: "",
            request.email ?: "",
            "",
            LocalDate.of(1970, 1, 1),
            request.howLongDancing ?: 0,
            mutableListOf(),
            request.howLongPlayingInstruments ?: 0,
            mutableListOf(),
            emptyList(),
            mutableListOf(),
            request.preferredLanguage ?: DEFAULT_PREFERRED_LANGUAGE,
            request.wantReceivePushNotifications ?: false,
            request.wantReceiveEmailNotifications ?: false,
            mutableSetOf(),
            mutableSetOf(),
            uid
        )
    }

    companion object {
        const val DUPLICATE_ACCOUNT_ERROR: String = "EMAIL_EXISTS"
        const val DEFAULT_PREFERRED_LANGUAGE: String = "pl_PL"
    }
}