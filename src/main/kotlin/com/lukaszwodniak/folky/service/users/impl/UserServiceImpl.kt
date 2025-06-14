package com.lukaszwodniak.folky.service.users.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest
import com.lukaszwodniak.folky.enums.UserType
import com.lukaszwodniak.folky.error.users.EmailInUseException
import com.lukaszwodniak.folky.model.DancingTeam
import com.lukaszwodniak.folky.model.User
import com.lukaszwodniak.folky.records.DancingTeamFiles
import com.lukaszwodniak.folky.records.RegisterDancingTeamUserRequest
import com.lukaszwodniak.folky.records.RegisterUserRequest
import com.lukaszwodniak.folky.records.UserData
import com.lukaszwodniak.folky.repository.RegionRepository
import com.lukaszwodniak.folky.repository.UserRepository
import com.lukaszwodniak.folky.security.AuthenticatedUserIdProvider
import com.lukaszwodniak.folky.service.dancingTeam.DancingTeamService
import com.lukaszwodniak.folky.service.users.UserService
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

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
    private val dancingTeamService: DancingTeamService,
    private val regionRepository: RegionRepository,
) : UserService {

    @Transactional
    override fun registerUser(registerRequest: RegisterUserRequest, type: UserType): User? {
        // TODO: Add validation to check is email already used
        val userUID = registerRequest.email?.let {
            registerRequest.password?.let { password ->
                createUserOnFirebase(
                    it,
                    password
                )
            }
        }
        val newUser = userUID?.let { createNewUser(it, registerRequest, type) }
        if (newUser != null) {
            return userRepository.saveAndFlush(newUser)
        }
        return null
    }

    @Transactional
    override fun registerDancingTeamUser(registerRequest: RegisterDancingTeamUserRequest) {
        val registerUserRequest = generateUserRegisterRequestFromDancingTeam(registerRequest)
        val user = registerUser(registerUserRequest, UserType.DANCING_TEAM)
        val dancingTeam = generateDancingTeam(registerRequest)
        val teamWithUser = dancingTeam.copy(accountUser = user, director = user)
        dancingTeamService.addTeam(teamWithUser, registerRequest.files ?: DancingTeamFiles())
    }

    override fun getUserFromContext(): User? {
        val userId = authenticatedUserIdProvider.userId
        return userRepository.findByUid(userId)
    }

    override fun getUserByEmail(email: String): User? =
        userRepository.findByEmail(email).orElseThrow { NoSuchElementException("No such user with email $email") }

    override fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElseThrow { NoSuchElementException("No such user with id $id") }
    }

    override fun editUser(existingUser: User, updateData: UserData): User? {
        val editedUser = existingUser.copy(
            firstName = updateData.firstName ?: existingUser.firstName,
            lastName = updateData.lastName ?: existingUser.lastName,
            wantReceivePushNotifications = updateData.wantReceivePushNotifications,
            wantReceiveEmailNotifications = updateData.wantReceiveEmailNotifications,
        )
        return userRepository.saveAndFlush(editedUser)
    }

    override fun getUsers(pageRequest: PageRequest, phrase: String?): Page<User> {
        return if (phrase.isNullOrBlank()) {
            userRepository.findAll(pageRequest)
        } else {
            userRepository.findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrEmailContainsIgnoreCase(
                phrase,
                phrase,
                phrase,
                pageRequest
            )
        }
    }

    override fun isContextAdmin(): Boolean {
        val contextUser = getUserFromContext()
        return contextUser?.let { user ->
            user.userRoles?.map { it.role.name }?.contains(ADMIN_ROLE)
        } ?: false
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
            throw exception
        }
    }

    private fun createNewUser(uid: String, request: RegisterUserRequest, type: UserType = UserType.NORMAL): User {
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
            request.preferredLanguage ?: DEFAULT_PREFERRED_LANGUAGE,
            request.wantReceivePushNotifications ?: false,
            request.wantReceiveEmailNotifications ?: false,
            mutableSetOf(),
            uid,
            type
        )
    }

    private fun generateUserRegisterRequestFromDancingTeam(registerDancingTeamUserRequest: RegisterDancingTeamUserRequest): RegisterUserRequest {
        return RegisterUserRequest(
            email = registerDancingTeamUserRequest.email,
            password = registerDancingTeamUserRequest.password,
            firstName = DANCING_TEAM_USER_FIRST_NAME,
            lastName = DANCING_TEAM_USER_LAST_NAME,
        )
    }

    private fun generateDancingTeam(registerDancingTeamUserRequest: RegisterDancingTeamUserRequest): DancingTeam {
        return DancingTeam(
            name = registerDancingTeamUserRequest.teamName,
            filesUUID = UUID.randomUUID(),
            description = registerDancingTeamUserRequest.teamDescription ?: "",
            creationDate = LocalDate.of(registerDancingTeamUserRequest.creationYear ?: 1900, 1, 1),
            region = registerDancingTeamUserRequest.region ?: regionRepository.findById(1)
                .orElseThrow { RuntimeException("No such region") },
            city = registerDancingTeamUserRequest.city ?: "",
            street = registerDancingTeamUserRequest.street ?: "",
            socialMedia = null
        )
    }

    companion object {
        const val DUPLICATE_ACCOUNT_ERROR: String = "EMAIL_EXISTS"
        const val DEFAULT_PREFERRED_LANGUAGE: String = "pl_PL"
        const val DANCING_TEAM_USER_FIRST_NAME: String = "DANCING_TEAM_FIRST_NAME"
        const val DANCING_TEAM_USER_LAST_NAME: String = "DANCING_TEAM_LAST_NAME"
        private const val ADMIN_ROLE: String = "admin"
    }
}