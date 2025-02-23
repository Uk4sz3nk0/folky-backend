package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * UserRepository
 * Created on: 2024-07-28
 * @author Łukasz Wodniak
 */

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUid(uid: String): User?
    fun findByEmail(email: String): Optional<User>
    fun findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrEmailContainsIgnoreCase(
        firstName: String,
        lastName: String,
        email: String,
        pageable: Pageable
    ): Page<User>
}