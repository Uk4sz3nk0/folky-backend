package com.lukaszwodniak.folky.repository

import com.lukaszwodniak.folky.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * UserRepository
 * Created on: 2024-07-28
 * @author Łukasz Wodniak
 */

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUid(uid: String): User?
}